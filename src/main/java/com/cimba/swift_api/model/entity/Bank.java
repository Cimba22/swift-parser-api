package com.cimba.swift_api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "banks", uniqueConstraints = {
        @UniqueConstraint(columnNames = "swiftCode")
})

public class Bank {

    @Id
    @Column(name = "swift_code", length = 11, nullable = false, unique = true)
//    @Size(min = 8, max = 11, message = "SWIFT code must be between 8 and 11 characters")
    private String swiftCode;

//    @Column(name = "address", length = 255)
    private String address;
//
//    @Column(name = "code_type" )
    private String codeType;

//    @Column(name = "bank_name", nullable = false, length = 100)
//    @NotBlank(message = "Bank name is required")
    private String bankName;

//    @Column(name = "country_iso2", nullable = false, length = 2)
//    @NotBlank(message = "Country ISO2 code is required")
//    @Pattern(regexp = "^[A-Z]{2}$", message = "Country ISO2 code must be exactly 2 uppercase letters")
    private String countryISO2;

//    @Column(name = "country_name", nullable = false, length = 100)
//    @NotBlank(message = "Country name is required")
    private String countryName;

    private boolean isHeadquarter;

    private String city;

    private String timezone;


    public Bank(String countryISO2, String swiftCode, String codeType, String bankName, String address, String city, String timezone, String countryName, boolean isHeadquarter) {
        this.countryISO2 = countryISO2.toUpperCase();
        this.swiftCode = swiftCode;
        this.codeType = codeType;
        this.bankName = bankName;
        this.address = address;
        this.city = city;
        this.timezone = timezone;
        this.countryName = countryName.toUpperCase();
        this.isHeadquarter = isHeadquarter;
    }

    public Bank() {

    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode( String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}

