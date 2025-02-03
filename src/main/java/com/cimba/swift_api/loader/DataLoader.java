package com.cimba.swift_api.loader;

import com.cimba.swift_api.service.SwiftCodeParserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final SwiftCodeParserService swiftCodeParser;

    public DataLoader(SwiftCodeParserService swiftCodeParser) {
        this.swiftCodeParser = swiftCodeParser;
    }

    @Override
    public void run(String... args) throws Exception {
        swiftCodeParser.parseAndSave("src/main/resources/Interns_2025_SWIFT_CODES - Sheet1.csv");
    }
}
