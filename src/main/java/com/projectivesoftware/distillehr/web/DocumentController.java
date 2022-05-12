package com.projectivesoftware.distillehr.web;

import com.projectivesoftware.distillehr.domain.*;
import com.projectivesoftware.distillehr.service.DocumentRepository;
import com.projectivesoftware.distillehr.service.EmpiService;
import com.projectivesoftware.distillehr.service.FacilityRepository;
import com.projectivesoftware.distillehr.utility.storage.DocumentNameFormatter;
import com.projectivesoftware.distillehr.utility.storage.ReadContext;
import com.projectivesoftware.distillehr.utility.storage.Storage;
import com.projectivesoftware.distillehr.utility.storage.WriteContext;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final String containerName;

    private final DocumentRepository documentRepository;

    private final FacilityRepository facilityRepository;

    private final Storage storage;

    private final EmpiService empiService;

    public DocumentController(@Value("${com.projectivesoftware.distillehr-document-storage-service.container-name}") String containerName,
                              DocumentRepository documentRepository,
                              FacilityRepository facilityRepository,
                              Storage storage,
                              EmpiService empiService) {
        Assert.notNull(containerName, "containerName must not be null!");
        Assert.notNull(documentRepository, "documentRepository must not be null!");
        Assert.notNull(facilityRepository, "facilityRepository must not be null!");
        Assert.notNull(storage, "storage must not be null!");
        Assert.notNull(empiService, "empiService must not be null!");
        this.containerName = containerName;
        this.documentRepository = documentRepository;
        this.facilityRepository = facilityRepository;
        this.storage = storage;
        this.empiService = empiService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public DocumentCreateResponse createDocument(@RequestBody DocumentCreateRequest documentCreateRequest) {
        Optional<Facility> facilityOptional = facilityRepository.findById(documentCreateRequest.getFacilityId());
        Facility facility = facilityOptional.orElse(null);

        if (facility == null) {
            return new DocumentCreateResponse(null, "No valid facility found");
        }

        if (documentCreateRequest.getPatientId() == null) {
            return new DocumentCreateResponse(null, "Invalid patientId");
        }

        if (documentCreateRequest.getEncounterId() == null) {
            return new DocumentCreateResponse(null, "Invalid encounterId");
        }

        if (documentCreateRequest.getDocumentTitle() == null) {
            return new DocumentCreateResponse(null, "Invalid documentTitle");
        }

        if (documentCreateRequest.getDocumentSubtitle() == null) {
            return new DocumentCreateResponse(null, "Invalid documentSubtitle");
        }

        if (documentCreateRequest.getServiceDate() == null) {
            return new DocumentCreateResponse(null, "Invalid serviceDate");
        }

        if (!(EnumUtils.isValidEnum(DocumentFormat.class, documentCreateRequest.getDocumentFormat().toString()))) {
            return new DocumentCreateResponse(null, "Invalid documentFormat");
        }

        if (!(EnumUtils.isValidEnum(DocumentType.class, documentCreateRequest.getDocumentType().toString()))) {
            return new DocumentCreateResponse(null, "Invalid documentType");
        }

        try {
            if (empiService.findByPersonId(documentCreateRequest.getPersonId()) == null) {
                return new DocumentCreateResponse(null, "Invalid personId");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DocumentCreateResponse(null, "empiService error");
        }

        Document document = new Document();

        try {
            WriteContext writeContext = storage.createWriteContext();
            writeContext.setDocumentType(documentCreateRequest.getDocumentType());
            writeContext.setDocumentParameters(documentCreateRequest.getDocumentParameters());
            storage.writeDocument(writeContext, documentCreateRequest.getDocumentContent());
            storage.close();

            document.setDocumentType(documentCreateRequest.getDocumentType());
            document.setDocumentTitle(documentCreateRequest.getDocumentTitle());
            document.setDocumentSubtitle(documentCreateRequest.getDocumentSubtitle());
            document.setPersonId(documentCreateRequest.getPersonId());
            document.setServiceDate(documentCreateRequest.getServiceDate());
            document.setDocumentFormat(documentCreateRequest.getDocumentFormat());
            document.setDocumentLocation(containerName);
            document.setDocumentFilename(DocumentNameFormatter.format(documentCreateRequest.getDocumentType(), documentCreateRequest.getDocumentParameters()));
            document.setFacility(facility);

            document = documentRepository.save(document);
        } catch (Exception e) {
            return new DocumentCreateResponse(null, "Error storing document");
        }

        return new DocumentCreateResponse(document.getDocumentId(), "Document created successfully");
    }

    @RequestMapping(value = "/content/{documentId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDocumentContent(@PathVariable(value = "documentId") Long documentId) throws IOException {
        Optional<Document> documentOptional = documentRepository.findById(documentId);
        Document document = documentOptional.orElse(null);

        if (document == null) {
            return null;
        }

        String filename = document.getDocumentFilename();
        ReadContext readContext = storage.createReadContext();
        readContext.setStoragePath(filename);
        byte[] documentContent = storage.readDocument(readContext);

        HttpHeaders httpHeaders = new HttpHeaders();

        switch (document.getDocumentFormat()) {
            case PDF:
                httpHeaders.setContentType(MediaType.APPLICATION_PDF);
                break;
            default:
                httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        }

        httpHeaders.setCacheControl("must-validate, post-check=0, pre-check=0");

        return new ResponseEntity<>(documentContent, httpHeaders, HttpStatus.OK);
    }

    /*
     * getDocumentTypesByPersonId returns a list of DocumentTypes by personId and the associated count
     * for each documentType in the result set. This is implemented as a Spring MVC controller instead of
     * a Spring Data REST method, as Spring Data REST does not support returning different types
     * than the repository type.
     */

    @RequestMapping(value = "/documentTypesByPersonId/{personId}", method = RequestMethod.GET)
    public List<DocumentTypeCount> getDocumentTypesByPersonId(@PathVariable(value = "personId") String personId) {
        return documentRepository.findDocumentTypeCountByPersonId(personId);
    }

    @RequestMapping(value = "/documentsByPersonId/{personId}", method = RequestMethod.GET)
    public List<Document> getDocumentsByPersonId(@PathVariable(value = "personId") String personId) {
        return documentRepository.findByPersonId(personId);
    }
}
