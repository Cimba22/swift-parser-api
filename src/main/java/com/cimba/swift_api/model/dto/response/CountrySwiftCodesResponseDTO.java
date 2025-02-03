package com.cimba.swift_api.model.dto.response;

import java.util.List;

public class CountrySwiftCodesResponseDTO {

    private String countryISO2;
    private String countryName;
    private List<BranchSwiftCodeResponseDTO> swiftCodes;

    public CountrySwiftCodesResponseDTO(String countryISO2, String countryName, List<BranchSwiftCodeResponseDTO> swiftCodes) {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = swiftCodes;
    }

    public CountrySwiftCodesResponseDTO() {

    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public List<BranchSwiftCodeResponseDTO> getSwiftCodes() {
        return swiftCodes;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setSwiftCodes(List<BranchSwiftCodeResponseDTO> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }
}

