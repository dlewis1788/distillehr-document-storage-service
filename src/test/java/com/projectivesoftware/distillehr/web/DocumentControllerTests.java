package com.projectivesoftware.distillehr.web;

import com.projectivesoftware.distillehr.domain.*;
import com.projectivesoftware.distillehr.service.CustomerEntityRepository;
import com.projectivesoftware.distillehr.service.DocumentRepository;
import com.projectivesoftware.distillehr.service.FacilityRepository;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class DocumentControllerTests {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private DocumentController documentController;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void verifyCreateDocumentShouldErrorWithInvalidFacility() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();
        Long invalidFacilityId = returnedFacilityId >> 1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        byte[] documentContent = new byte[] { 0x00 };

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        "Test Document Subtitle",
                        123L,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        invalidFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getDocumentId()).isEqualTo(null);
        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("No valid facility found");
    }

    @Test
    public void verifyCreateDocumentShouldErrorWithInvalidPatientId() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        byte[] documentContent = new byte[] { 0x00 };

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        "Test Document Subtitle",
                        null,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getDocumentId()).isEqualTo(null);
        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Invalid patientId");
    }

    @Test
    public void verifyCreateDocumentShouldErrorWithInvalidEncounterId() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        byte[] documentContent = new byte[] { 0x00 };

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        "Test Document Subtitle",
                        123L,
                        null,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getDocumentId()).isEqualTo(null);
        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Invalid encounterId");
    }

    @Test
    public void verifyCreateDocumentShouldErrorWithInvalidDocumentTitle() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        byte[] documentContent = new byte[] { 0x00 };

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        null,
                        "Test Document Subtitle",
                        123L,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getDocumentId()).isEqualTo(null);
        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Invalid documentTitle");
    }

    @Test
    public void verifyCreateDocumentShouldErrorWithInvalidDocumentSubtitle() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        byte[] documentContent = new byte[] { 0x00 };

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        null,
                        123L,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getDocumentId()).isEqualTo(null);
        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Invalid documentSubtitle");
    }

    @Test
    public void verifyCreateDocumentShouldErrorWithInvalidServiceDate() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        byte[] documentContent = new byte[] { 0x00 };

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        "Test Document Subtitle",
                        123L,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        null,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getDocumentId()).isEqualTo(null);
        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Invalid serviceDate");
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void verifyCreateDocumentShouldBeSuccessfulWithValidDocumentCreateRequest() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        Resource resource;
        InputStream inputStream;
        byte[] documentContent = new byte[0];

        try {
            resource = resourceLoader.getResource("classpath:test-documents/MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT_123123.pdf");
            inputStream = resource.getInputStream();
            documentContent = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            fail("Error reading test PDF");
        }

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        "Test Document Subtitle",
                        123L,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Document created successfully");

        Document requestedDocument = documentRepository.findById(documentCreateResponse.getDocumentId()).orElse(null);

        assertThat(requestedDocument).isNotNull();
    }

    @Test
    public void verifyGetDocumentContentShouldReturnNullWithInvalidDocumentId() {
        try {
            ResponseEntity<byte[]> responseEntity = documentController.getDocumentContent(9999L);
            assertThat(responseEntity).isNull();
        } catch (IOException e) {
            fail("Non-Null value returned for invalid document");
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void verifyGetDocumentContentShouldReturnSameDocumentStoredWithCreateDocument() {
        CustomerEntity customerEntity = customerEntityRepository.save(
                new CustomerEntity(
                        "PS",
                        "Projective Software",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067",
                        "111-111-1111",
                        "John Doe"));
        Facility facility = facilityRepository.save(
                new Facility(
                        customerEntity,
                        "DistillEHR Test Facility",
                        "Test Address",
                        "Franklin",
                        "TN",
                        "37067"));

        Long returnedFacilityId = facility.getFacilityId();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-DD");

        Date serviceDate = new Date();

        try {
            serviceDate = simpleDateFormat.parse("17-01-01");
        } catch (ParseException e) {
            fail("Error parsing date for test");
        }

        List<String> documentParameters = new ArrayList<>();
        documentParameters.add("123");
        documentParameters.add("123123");

        Resource resource;
        InputStream inputStream;
        byte[] documentContent = new byte[0];

        try {
            resource = resourceLoader.getResource("classpath:test-documents/MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT_123123.pdf");
            inputStream = resource.getInputStream();
            documentContent = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            fail("Error reading test PDF");
        }

        DocumentCreateRequest documentCreateRequest =
                new DocumentCreateRequest(
                        "Test Document Title",
                        "Test Document Subtitle",
                        123L,
                        123123L,
                        "bcfbdbd0-6b69-11e7-b933-005056a06f55",
                        serviceDate,
                        DocumentFormat.PDF,
                        returnedFacilityId,
                        DocumentType.MEDHOST_ENTERPRISE_FULL_ENCOUNTER_EXPORT,
                        documentParameters,
                        documentContent);

        DocumentCreateResponse documentCreateResponse = documentController.createDocument(documentCreateRequest);

        assertThat(documentCreateResponse.getReturnStatus()).isEqualTo("Document created successfully");

        Document requestedDocument = documentRepository.findById(documentCreateResponse.getDocumentId()).orElse(null);

        assertThat(requestedDocument).isNotNull();

        try {
            ResponseEntity<byte[]> responseEntity = documentController.getDocumentContent(requestedDocument.getDocumentId());
            byte[] responseDocumentContent = responseEntity.getBody();
            assertThat(Arrays.equals(documentContent, responseDocumentContent)).isEqualTo(true);
        } catch (IOException e) {
            fail("Error reading documentContent");
        }
    }
}
