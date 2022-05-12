package com.projectivesoftware.distillehr.utility.storage;

import java.io.IOException;

public class StorageException extends IOException {

    public StorageException(IOException cause) {
        super(cause);
    }
}
