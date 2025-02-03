package com.cimba.swift_api.exception;

public class SwiftCodeNotFoundException extends RuntimeException {
    public SwiftCodeNotFoundException(String swiftCode) {
        super("Bank not found with SWIFT code: " + swiftCode);
    }
}