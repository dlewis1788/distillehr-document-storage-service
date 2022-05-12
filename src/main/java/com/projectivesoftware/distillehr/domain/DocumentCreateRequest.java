package com.projectivesoftware.distillehr.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

public class DocumentCreateRequest {

    private String documentTitle;

    private String documentSubtitle;

    private Long patientId;

    private Long encounterId;

    private String personId;

    private Date serviceDate;

    private DocumentFormat documentFormat;

    private Long facilityId;

    private DocumentType documentType;

    private List<String> documentParameters;

    private byte[] documentContent;

    public DocumentCreateRequest() {

    }

    public DocumentCreateRequest(String documentTitle, String documentSubtitle, Long patientId, Long encounterId, String personId, Date serviceDate, DocumentFormat documentFormat, Long facilityId, DocumentType documentType, List<String> documentParameters, byte[] documentContent) {
        this.documentTitle = documentTitle;
        this.documentSubtitle = documentSubtitle;
        this.patientId = patientId;
        this.encounterId = encounterId;
        this.personId = personId;
        this.serviceDate = serviceDate;
        this.documentFormat = documentFormat;
        this.facilityId = facilityId;
        this.documentType = documentType;
        this.documentParameters = documentParameters;
        this.documentContent = documentContent;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentSubtitle() {
        return documentSubtitle;
    }

    public void setDocumentSubtitle(String documentSubtitle) {
        this.documentSubtitle = documentSubtitle;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public DocumentFormat getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(DocumentFormat documentFormat) {
        this.documentFormat = documentFormat;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public List<String> getDocumentParameters() {
        return documentParameters;
    }

    public void setDocumentParameters(List<String> documentParameters) {
        this.documentParameters = documentParameters;
    }

    public byte[] getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(byte[] documentContent) {
        this.documentContent = documentContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("documentTitle", documentTitle)
                .append("documentSubtitle", documentSubtitle)
                .append("patientId", patientId)
                .append("encounterId", encounterId)
                .append("personId", personId)
                .append("serviceDate", serviceDate)
                .append("documentFormat", documentFormat)
                .append("facilityId", facilityId)
                .append("documentType", documentType)
                .append("documentParameters", documentParameters)
                .toString();
    }
}
