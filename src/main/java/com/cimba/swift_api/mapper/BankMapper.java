package com.cimba.swift_api.mapper;

import com.cimba.swift_api.model.entity.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {

    public Bank mapToBank(String[] row) {
        String countryISO2 = row[0];
        String swiftCode = row[1];
        String codeType = row[2];
        String bankName = row[3];
        String address = row[4];
        String city = row[5];
        String countryName = row[6];
        String timezone = row[7];
        boolean isHeadquarter = swiftCode.endsWith("XXX");

        return new Bank(countryISO2, swiftCode, codeType, bankName, address, city, countryName, timezone, isHeadquarter);
    }
}
