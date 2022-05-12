package com.projectivesoftware.distillehr.domain;

public class DocumentCreateResponse {

    private Long documentId;

    private String returnStatus;

    public DocumentCreateResponse() {

    }

    public DocumentCreateResponse(Long documentId, String returnStatus) {
        this.documentId = documentId;
        this.returnStatus = returnStatus;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }
}
