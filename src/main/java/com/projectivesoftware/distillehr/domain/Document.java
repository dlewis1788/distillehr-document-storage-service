package com.projectivesoftware.distillehr.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Document implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_id_seq")
    @SequenceGenerator(name = "document_id_seq", sequenceName = "document_id_seq", allocationSize = 1)
    private Long documentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    private String documentTitle;

    private String documentSubtitle;

    @Column(nullable = false)
    private String personId;

    @Temporal(TemporalType.DATE)
    private Date serviceDate;

    private Long numberOfPages;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentFormat documentFormat;

    @Column(nullable = false)
    private String documentLocation;

    @Column(nullable = false)
    private String documentFilename;

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
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

    public Long getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Long numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public DocumentFormat getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(DocumentFormat documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getDocumentLocation() {
        return documentLocation;
    }

    public void setDocumentLocation(String documentLocation) {
        this.documentLocation = documentLocation;
    }

    public String getDocumentFilename() {
        return documentFilename;
    }

    public void setDocumentFilename(String documentFilename) {
        this.documentFilename = documentFilename;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("documentId", documentId)
                .append("documentType", documentType)
                .append("documentTitle", documentTitle)
                .append("documentSubtitle", documentSubtitle)
                .append("personId", personId)
                .append("serviceDate", serviceDate)
                .append("numberOfPages", numberOfPages)
                .append("documentFormat", documentFormat)
                .append("documentLocation", documentLocation)
                .append("documentFilename", documentFilename)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return new EqualsBuilder()
                .append(documentId, document.documentId)
                .append(documentType, document.documentType)
                .append(documentTitle, document.documentTitle)
                .append(documentSubtitle, document.documentSubtitle)
                .append(personId, document.personId)
                .append(serviceDate, document.serviceDate)
                .append(numberOfPages, document.numberOfPages)
                .append(documentFormat, document.documentFormat)
                .append(documentLocation, document.documentLocation)
                .append(documentFilename, document.documentFilename)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(documentId)
                .append(documentType)
                .append(documentTitle)
                .append(documentSubtitle)
                .append(personId)
                .append(serviceDate)
                .append(numberOfPages)
                .append(documentFormat)
                .append(documentLocation)
                .append(documentFilename)
                .toHashCode();
    }
}
