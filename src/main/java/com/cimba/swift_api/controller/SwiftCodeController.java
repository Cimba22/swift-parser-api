package com.cimba.swift_api.controller;


import com.cimba.swift_api.model.dto.request.AddSwiftCodeRequestDTO;
import com.cimba.swift_api.model.dto.response.AddSwiftCodeResponseDTO;
import com.cimba.swift_api.model.dto.response.DeleteSwiftCodeResponseDTO;
import com.cimba.swift_api.service.SwiftCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private final SwiftCodeService swiftCodeService;

    @Autowired
    public SwiftCodeController(SwiftCodeService swiftCodeService) {
        this.swiftCodeService = swiftCodeService;
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<?> getSwiftCodeDetails(@PathVariable String swiftCode) {
        return swiftCodeService.getSwiftCodeDetails(swiftCode);
    }

    @GetMapping("/country/{countryISO2}")
    public ResponseEntity<?> getSwiftCodeDetailsByCountry(@PathVariable String countryISO2) {
        return swiftCodeService.getSwiftCodeDetailsByCountryISO2(countryISO2);
    }

    @PostMapping("/")
    public ResponseEntity<AddSwiftCodeResponseDTO> addSwiftCode(@Valid @RequestBody AddSwiftCodeRequestDTO requestDTO) {
        AddSwiftCodeResponseDTO response = swiftCodeService.addSwiftCode(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<DeleteSwiftCodeResponseDTO> deleteSwiftCode(
            @PathVariable String swiftCode,
            @RequestParam String bankName,
            @RequestParam String countryISO2) {
        return swiftCodeService.deleteSwiftCode(swiftCode, bankName, countryISO2);
    }
}


