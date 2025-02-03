package com.cimba.swift_api.model.dto.response;

import java.util.List;

public class HeadquarterSwiftCodeResponseDTO {

    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;
    private List<BranchSwiftCodeResponseDTO> branches;

    public HeadquarterSwiftCodeResponseDTO(String address, String bankName, String countryISO2, String countryName,
                                           boolean isHeadquarter, String swiftCode, List<BranchSwiftCodeResponseDTO> branches) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
        this.branches = branches;
    }

    public HeadquarterSwiftCodeResponseDTO() {

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

    public String getCountryName() {
        return countryName;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public List<BranchSwiftCodeResponseDTO> getBranches() {
        return branches;
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

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setIsHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setBranches(List<BranchSwiftCodeResponseDTO> branches) {
        this.branches = branches;
    }
}
