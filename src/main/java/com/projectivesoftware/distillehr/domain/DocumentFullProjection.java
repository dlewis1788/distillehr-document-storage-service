package com.projectivesoftware.distillehr.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "full", types = Document.class )
public interface DocumentFullProjection {

    Long getDocumentId();

    DocumentType getDocumentType();

    String getDocumentTitle();

    String getDocumentSubtitle();

    Long getPatientId();

    Long getEncounterId();

    Date getServiceDate();

    Long getNumberOfPages();

    DocumentFormat getDocumentFormat();

    String getDocumentLocation();

    String getDocumentFilename();

    Facility getFacility();
}
