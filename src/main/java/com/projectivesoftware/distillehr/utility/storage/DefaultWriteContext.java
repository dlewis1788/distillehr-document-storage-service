package com.projectivesoftware.distillehr.utility.storage;

import com.projectivesoftware.distillehr.domain.DocumentType;

import java.util.List;

public class DefaultWriteContext extends DefaultReadContext implements WriteContext {

    private DocumentType documentType;

    private List<String> documentParameters;

    public DefaultWriteContext(Storage storage) {
        super(storage);
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public List<String> getDocumentParameters() {
        return documentParameters;
    }

    @Override
    public void setDocumentParameters(List<String> documentParameters) {
        this.documentParameters = documentParameters;
    }
}
