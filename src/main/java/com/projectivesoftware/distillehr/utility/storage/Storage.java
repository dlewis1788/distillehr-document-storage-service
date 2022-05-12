package com.projectivesoftware.distillehr.utility.storage;

import java.io.Closeable;

public interface Storage extends Closeable {

    WriteContext createWriteContext();

    ReadContext createReadContext();

    boolean isAccessible();

    byte[] readDocument(ReadContext readContext) throws StorageException;

    void writeDocument(WriteContext writeContext, byte[] documentContent) throws StorageException;
}
