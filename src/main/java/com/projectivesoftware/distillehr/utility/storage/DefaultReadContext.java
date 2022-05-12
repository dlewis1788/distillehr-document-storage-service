package com.projectivesoftware.distillehr.utility.storage;

public class DefaultReadContext implements ReadContext {

    private Storage storage;

    private String storagePath;

    public DefaultReadContext(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public String getStoragePath() {
        return storagePath;
    }

    @Override
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
