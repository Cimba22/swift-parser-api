package com.cimba.swift_api.service;

import com.cimba.swift_api.mapper.BankMapper;
import com.cimba.swift_api.model.entity.Bank;
import com.cimba.swift_api.validator.RowValidator;
import com.cimba.swift_api.parser.CSVFileParser;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SwiftCodeParserService {

    private static final Logger logger = LoggerFactory.getLogger(SwiftCodeParserService.class);

    private final SwiftCodeService swiftCodeService;
    private final CSVFileParser csvFileParser;
    private final RowValidator rowValidator;
    private final BankMapper bankMapper;

    @Autowired
    public SwiftCodeParserService(SwiftCodeService swiftCodeService,
                                  CSVFileParser csvFileParser,
                                  RowValidator rowValidator,
                                  BankMapper bankMapper) {
        this.swiftCodeService = swiftCodeService;
        this.csvFileParser = csvFileParser;
        this.rowValidator = rowValidator;
        this.bankMapper = bankMapper;
    }

    public void parseAndSave(String filePath) throws IOException, CsvException {
        logger.info("Starting parsing of file: {}", filePath);

        List<String[]> rows = csvFileParser.parseFile(filePath);
        List<Bank> banks = new ArrayList<>();

        for (String[] row : rows) {
            if (rowValidator.isValid(row)) {
                Bank bank = bankMapper.mapToBank(row);
                banks.add(bank);
            } else {
                logger.warn("Invalid row skipped: {}", (Object) row);
            }
        }

        swiftCodeService.saveAll(banks);
        logger.info("Parsing completed. Total banks saved: {}", banks.size());
    }
}
