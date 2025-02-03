package com.cimba.swift_api.model.dto.response;

public class AddSwiftCodeResponseDTO {

    private String message;

    public AddSwiftCodeResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String s) {
        this.message = message;
    }
}
