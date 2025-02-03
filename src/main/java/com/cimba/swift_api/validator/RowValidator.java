package com.cimba.swift_api.validator;


import org.springframework.stereotype.Component;

@Component
public class RowValidator {

    public boolean isValid(String[] row) {
        return row.length == 8
                && row[0] != null && row[0].matches("[A-Z]{2}")
                && row[1] != null && (row[1].length() == 8 || row[1].length() == 11)
                && row[3] != null && !row[3].isEmpty();
    }
}
