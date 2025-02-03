package com.cimba.swift_api.service;

import com.cimba.swift_api.mapper.BankMapper;
import com.cimba.swift_api.model.entity.Bank;
import com.cimba.swift_api.parser.CSVFileParser;
import com.cimba.swift_api.validator.RowValidator;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeParserServiceTest {

    @Mock
    private SwiftCodeService swiftCodeService;

    @Mock
    private CSVFileParser csvFileParser;

    @Mock
    private RowValidator rowValidator;

    @Mock
    private BankMapper bankMapper;

    @InjectMocks
    private SwiftCodeParserService parserService;

    @Test
    void testParseAndSave_validAndInvalidRows() throws IOException, CsvException {
        String[] validRow = {
                "US",                // COUNTRY ISO2 CODE
                "TESTUS33XXX",       // SWIFT CODE
                "CODE_TYPE",         // CODE TYPE
                "Test Bank",         // NAME
                "123 Main Street",   // ADDRESS
                "Some Town",         // TOWN NAME
                "United States",     // COUNTRY NAME
                "America/New_York"   // TIME ZONE
        };
        String[] invalidRow = { "INVALID", "DATA" };

        List<String[]> rows = Arrays.asList(validRow, invalidRow);

        when(csvFileParser.parseFile("test.csv")).thenReturn(rows);
        when(rowValidator.isValid(validRow)).thenReturn(true);
        when(rowValidator.isValid(invalidRow)).thenReturn(false);

        Bank bank = new Bank();
        bank.setCountryISO2("US");
        bank.setSwiftCode("TESTUS33XXX");
        bank.setBankName("Test Bank");
        bank.setAddress("123 Main Street");
        bank.setCountryName("United States");
        bank.setHeadquarter(true);
        when(bankMapper.mapToBank(validRow)).thenReturn(bank);

        parserService.parseAndSave("test.csv");

        verify(swiftCodeService, times(1)).saveAll(List.of(bank));
        verify(bankMapper, never()).mapToBank(invalidRow);
    }
}
