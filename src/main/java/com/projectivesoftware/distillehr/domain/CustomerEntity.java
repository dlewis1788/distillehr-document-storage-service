package com.projectivesoftware.distillehr.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CustomerEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_entity_id_seq")
    @SequenceGenerator(name = "customer_entity_id_seq", sequenceName = "customer_entity_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String mnemonic;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String contactName;

    public CustomerEntity() {

    }

    public CustomerEntity(String mnemonic, String name, String streetAddress, String city, String state, String zipCode, String phoneNumber, String contactName) {
        this.mnemonic = mnemonic;
        this.name = name;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.contactName = contactName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("mnemonic", mnemonic)
                .append("name", name)
                .append("streetAddress", streetAddress)
                .append("city", city)
                .append("state", state)
                .append("zipCode", zipCode)
                .append("phoneNumber", phoneNumber)
                .append("contactName", contactName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(mnemonic, that.mnemonic)
                .append(name, that.name)
                .append(streetAddress, that.streetAddress)
                .append(city, that.city)
                .append(state, that.state)
                .append(zipCode, that.zipCode)
                .append(phoneNumber, that.phoneNumber)
                .append(contactName, that.contactName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(mnemonic)
                .append(name)
                .append(streetAddress)
                .append(city)
                .append(state)
                .append(zipCode)
                .append(phoneNumber)
                .append(contactName)
                .toHashCode();
    }
}
