package com.cimba.swift_api.model.dto.response;

public class BranchSwiftCodeResponseDTO {

    private String address;
    private String bankName;
    private String countryISO2;
    private boolean isHeadquarter;
    private String swiftCode;

    public BranchSwiftCodeResponseDTO(String address, String bankName, String countryISO2, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
