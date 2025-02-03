package com.cimba.swift_api.model.dto.response;

public class DeleteSwiftCodeResponseDTO {

    private String message;

    public DeleteSwiftCodeResponseDTO() {
    }

    public DeleteSwiftCodeResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
