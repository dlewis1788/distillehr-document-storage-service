package com.projectivesoftware.distillehr.configuration.storage;

import com.projectivesoftware.distillehr.utility.storage.FilesystemStorage;
import com.projectivesoftware.distillehr.utility.storage.Storage;
import com.projectivesoftware.distillehr.utility.storage.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import java.io.IOException;

@Configuration
public class StorageFactory {

    private final String storageProvider;

    private final String filesystemStorageProviderBaseDirectory;

    private final String containerName;

    public StorageFactory(@Value("${com.projectivesoftware.distillehr-document-storage-service.storage-provider}") String storageProvider,
                          @Value("${com.projectivesoftware.distillehr-document-storage-service.filesystem-storage-provider.base-directory}") String filesystemStorageProviderBaseDirectory,
                          @Value("${com.projectivesoftware.distillehr-document-storage-service.container-name}") String containerName) {
        Assert.notNull(storageProvider, "storageProvider must not be null!");
        Assert.notNull(filesystemStorageProviderBaseDirectory, "filesystemStorageProviderBaseDirectory must not be null!");
        Assert.notNull(containerName, "containerName must not be null!");
        this.storageProvider = storageProvider;
        this.filesystemStorageProviderBaseDirectory = filesystemStorageProviderBaseDirectory;
        this.containerName = containerName;
    }

    @Bean
    @Scope("prototype")
    public Storage getStorageProvider() throws StorageException {

        if (storageProvider == null) {
            throw new StorageException(new IOException("Storage provider must be specified."));
        }

        if (containerName == null) {
            throw new StorageException(new IOException("Container name cannot be null."));
        }

        switch (storageProvider) {
            case "FilesystemStorage":
                if (filesystemStorageProviderBaseDirectory == null) {
                    throw new StorageException(new IOException("Base directory for filesystem storage provider cannot be null."));
                }

                return new FilesystemStorage(filesystemStorageProviderBaseDirectory, containerName);
            default:
                throw new StorageException(new IOException("Invalid storage provider specified."));
        }
    }
}
