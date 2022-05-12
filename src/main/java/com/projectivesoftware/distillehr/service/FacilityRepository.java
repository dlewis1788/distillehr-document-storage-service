package com.projectivesoftware.distillehr.service;

import com.projectivesoftware.distillehr.domain.Facility;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends PagingAndSortingRepository<Facility, Long> {

}
