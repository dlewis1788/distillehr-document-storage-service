package com.projectivesoftware.distillehr.domain;

public class DocumentTypeCount {

    private DocumentType documentType;

    private Long count;

    public DocumentTypeCount(DocumentType documentType, Long count) {
        this.documentType = documentType;
        this.count = count;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
