package com.projectivesoftware.distillehr.utility.storage;

import com.google.common.io.ByteSource;
import org.apache.commons.io.IOUtils;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.filesystem.reference.FilesystemConstants;

import java.io.IOException;
import java.util.Properties;

public class FilesystemStorage implements Storage {

    private BlobStoreContext blobStoreContext;

    private String container;

    public FilesystemStorage(String baseDirectory, String container) {
        super();

        this.container = container;

        Properties properties = new Properties();
        properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, baseDirectory);

        blobStoreContext = ContextBuilder.newBuilder("filesystem")
                .overrides(properties)
                .buildView(BlobStoreContext.class);
    }

    @Override
    public WriteContext createWriteContext() {
        return new DefaultWriteContext(this);
    }

    @Override
    public ReadContext createReadContext() {
        return new DefaultReadContext(this);
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    @Override
    public byte[] readDocument(ReadContext readContext) throws StorageException {
        BlobStore blobStore = blobStoreContext.getBlobStore();
        Blob blob = blobStore.getBlob(container, readContext.getStoragePath());

        if (blob == null) {
            throw new StorageException(new IOException("Object " + readContext.getStoragePath() + " not found."));
        }

        byte[] documentContent;

        try {
            documentContent = IOUtils.toByteArray(blob.getPayload().openStream());
        } catch (IOException e) {
            throw new StorageException(e);
        }

        return documentContent;
    }

    @Override
    public void writeDocument(WriteContext writeContext, byte[] documentContent) throws StorageException {
        BlobStore blobStore = blobStoreContext.getBlobStore();

        if (!blobStore.containerExists(container)) {
            blobStore.createContainerInLocation(null, container);
        }

        try {
            String blobName = DocumentNameFormatter.format(writeContext.getDocumentType(), writeContext.getDocumentParameters());
            ByteSource byteSource = ByteSource.wrap(documentContent);
            Blob blob = blobStore.blobBuilder(blobName).payload(byteSource).contentLength(byteSource.size()).build();
            blobStore.putBlob(container, blob);
        } catch (Exception e) {
            throw new StorageException(new IOException(e.getCause()));
        }
    }

    @Override
    public void close() throws IOException {
        blobStoreContext.close();
    }
}
