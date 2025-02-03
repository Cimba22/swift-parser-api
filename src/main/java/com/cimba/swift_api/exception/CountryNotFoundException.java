package com.cimba.swift_api.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String countryISO2) {
        super("No SWIFT codes found for country " + countryISO2);
    }
}
