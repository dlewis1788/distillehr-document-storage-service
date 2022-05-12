package com.projectivesoftware.distillehr.utility.storage;

public interface ReadContext {

    Storage getStorage();

    String getStoragePath();

    void setStoragePath(String storagePath);
}
