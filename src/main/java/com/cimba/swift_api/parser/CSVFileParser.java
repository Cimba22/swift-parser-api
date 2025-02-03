package com.cimba.swift_api.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class CSVFileParser {

    private static final Logger logger = LoggerFactory.getLogger(CSVFileParser.class);

    public List<String[]> parseFile(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            logger.info("Reading CSV file: {}", filePath);
            return reader.readAll();
        } catch (IOException | CsvException e) {
            logger.error("Error occurred while reading file: {}", filePath, e);
            throw e;
        }
    }
}
