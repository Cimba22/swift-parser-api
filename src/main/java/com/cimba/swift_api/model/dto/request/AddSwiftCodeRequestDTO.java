package com.cimba.swift_api.model.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class AddSwiftCodeRequestDTO {

    @NotEmpty(message = "Address is required.")
    private String address;

    @NotEmpty(message = "Bank name is required.")
    private String bankName;

    @NotEmpty(message = "Country ISO2 code is required.")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country ISO2 code must be 2 uppercase letters.")
    private String countryISO2;

    @NotEmpty(message = "Country name is required.")
    private String countryName;

    private boolean isHeadquarter;

    @NotEmpty(message = "SWIFT code is required.")
    private String swiftCode;

    public AddSwiftCodeRequestDTO() {}

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
        this.countryISO2 = countryISO2.toUpperCase();
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

    public void setIsHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
