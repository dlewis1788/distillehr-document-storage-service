package com.projectivesoftware.distillehr.utility.storage;

import com.projectivesoftware.distillehr.domain.DocumentType;

import java.util.List;

public interface WriteContext extends ReadContext {

    DocumentType getDocumentType();

    void setDocumentType(DocumentType documentType);

    List<String> getDocumentParameters();

    void setDocumentParameters(List<String> documentParameters);
}
