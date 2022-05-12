package com.projectivesoftware.distillehr.service;

import com.projectivesoftware.distillehr.domain.Document;
import com.projectivesoftware.distillehr.domain.DocumentTypeCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

    List<Document> findByPersonId(@Param("personId") String personId);

    @RestResource(path = "personId")
    Page<Document> findByPersonId(@Param("personId") String personId, Pageable pageable);

    @Query("select new com.projectivesoftware.distillehr.domain.DocumentTypeCount(d.documentType, count(d)) from Document d where d.personId = :personId group by d.documentType")
    List<DocumentTypeCount> findDocumentTypeCountByPersonId(@Param("personId") String personId);
}