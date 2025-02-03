package com.cimba.swift_api.exception;

public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException() {
        super("Missing required fields: countryISO2, swiftCode, or bankName");
    }
}
