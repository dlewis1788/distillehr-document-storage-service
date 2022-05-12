package com.projectivesoftware.distillehr.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Facility implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_id_seq")
    @SequenceGenerator(name = "facility_id_seq", sequenceName = "facility_id_seq", allocationSize = 1)
    private Long facilityId;

    @ManyToOne
    @JoinColumn(name = "customer_entity_id", nullable = false)
    private CustomerEntity customerEntity;

    @Column(nullable = false)
    private String facilityName;

    @Column(nullable = false)
    private String facilityStreetAddress;

    @Column(nullable = false)
    private String facilityCity;

    @Column(nullable = false)
    private String facilityState;

    @Column(nullable = false)
    private String facilityZipCode;

    public Facility() {

    }

    public Facility(CustomerEntity customerEntity, String facilityName, String facilityStreetAddress, String facilityCity, String facilityState, String facilityZipCode) {
        this.customerEntity = customerEntity;
        this.facilityName = facilityName;
        this.facilityStreetAddress = facilityStreetAddress;
        this.facilityCity = facilityCity;
        this.facilityState = facilityState;
        this.facilityZipCode = facilityZipCode;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityStreetAddress() {
        return facilityStreetAddress;
    }

    public void setFacilityStreetAddress(String facilityStreetAddress) {
        this.facilityStreetAddress = facilityStreetAddress;
    }

    public String getFacilityCity() {
        return facilityCity;
    }

    public void setFacilityCity(String facilityCity) {
        this.facilityCity = facilityCity;
    }

    public String getFacilityState() {
        return facilityState;
    }

    public void setFacilityState(String facilityState) {
        this.facilityState = facilityState;
    }

    public String getFacilityZipCode() {
        return facilityZipCode;
    }

    public void setFacilityZipCode(String facilityZipCode) {
        this.facilityZipCode = facilityZipCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("facilityId", facilityId)
                .append("customerEntity", customerEntity)
                .append("facilityName", facilityName)
                .append("facilityStreetAddress", facilityStreetAddress)
                .append("facilityCity", facilityCity)
                .append("facilityState", facilityState)
                .append("facilityZipCode", facilityZipCode)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Facility facility = (Facility) o;

        return new EqualsBuilder()
                .append(facilityId, facility.facilityId)
                .append(facilityName, facility.facilityName)
                .append(facilityStreetAddress, facility.facilityStreetAddress)
                .append(facilityCity, facility.facilityCity)
                .append(facilityState, facility.facilityState)
                .append(facilityZipCode, facility.facilityZipCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(facilityId)
                .append(facilityName)
                .append(facilityStreetAddress)
                .append(facilityCity)
                .append(facilityState)
                .append(facilityZipCode)
                .toHashCode();
    }
}
