package com.cimba.swift_api.exception;

public class DuplicateSwiftCodeException extends Throwable {
    public DuplicateSwiftCodeException(String swiftCode) {
        super("Bank already exist with swift-code: " + swiftCode);
    }
}
