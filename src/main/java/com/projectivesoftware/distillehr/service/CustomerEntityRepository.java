package com.projectivesoftware.distillehr.service;

import com.projectivesoftware.distillehr.domain.CustomerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepository extends PagingAndSortingRepository<CustomerEntity, Long> {

}
