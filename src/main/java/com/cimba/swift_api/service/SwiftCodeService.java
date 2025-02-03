package com.cimba.swift_api.service;

import com.cimba.swift_api.exception.CountryNotFoundException;
import com.cimba.swift_api.exception.EntityNotFoundException;
import com.cimba.swift_api.exception.MissingRequiredFieldsException;
import com.cimba.swift_api.exception.SwiftCodeNotFoundException;
import com.cimba.swift_api.mapper.SwiftCodeMapper;
import com.cimba.swift_api.model.dto.request.AddSwiftCodeRequestDTO;
import com.cimba.swift_api.model.dto.response.*;
import com.cimba.swift_api.model.entity.Bank;
import com.cimba.swift_api.repository.BankRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwiftCodeService {

    private final BankRepository bankRepository;
    private final SwiftCodeMapper swiftCodeMapper;

    public SwiftCodeService(BankRepository bankRepository, SwiftCodeMapper swiftCodeMapper) {
        this.bankRepository = bankRepository;
        this.swiftCodeMapper = swiftCodeMapper;
    }

    public void saveAll(List<Bank> banks) {
        bankRepository.saveAll(banks);
    }

    public ResponseEntity<?> getSwiftCodeDetails(String swiftCode) {

        Bank bank = bankRepository.findBySwiftCode(swiftCode)
                .orElseThrow(() -> new SwiftCodeNotFoundException(swiftCode));

        if (bank.isHeadquarter()) {

            String swiftCodePrefix = swiftCode.substring(0, swiftCode.length() - 3);

            List<Bank> branchEntities = bankRepository.findBranchesByBankNameAndCountry(
                    bank.getBankName(), bank.getCountryISO2(), swiftCodePrefix
            );


            List<BranchSwiftCodeResponseDTO> branchDTOs = branchEntities.stream()
                    .filter(b -> !b.getSwiftCode().equals(swiftCode))
                    .map(swiftCodeMapper::toBranchDto)
                    .collect(Collectors.toList());


            HeadquarterSwiftCodeResponseDTO response = new HeadquarterSwiftCodeResponseDTO();
            response.setAddress(bank.getAddress());
            response.setBankName(bank.getBankName());
            response.setCountryISO2(bank.getCountryISO2());
            response.setCountryName(bank.getCountryName());
            response.setIsHeadquarter(true);
            response.setSwiftCode(bank.getSwiftCode());
            response.setBranches(branchDTOs);

            return ResponseEntity.ok(response);
        } else {
            BranchSwiftCodeResponseDTO branchResponse = swiftCodeMapper.toBranchDto(bank);
            return ResponseEntity.ok(branchResponse);
        }
    }

    public ResponseEntity<CountrySwiftCodesResponseDTO> getSwiftCodeDetailsByCountryISO2(String countryISO2) {
        String normalizedISO2 = countryISO2.toUpperCase();

        List<Bank> banks = bankRepository.findByCountryISO2(normalizedISO2);
        if (banks.isEmpty()) {
            throw new CountryNotFoundException(normalizedISO2);
        }

        List<BranchSwiftCodeResponseDTO> swiftCodes = banks.stream()
                .map(swiftCodeMapper::toBranchDto)
                .collect(Collectors.toList());

        String countryName = banks.get(0).getCountryName();

        CountrySwiftCodesResponseDTO responseDTO = new CountrySwiftCodesResponseDTO();
        responseDTO.setCountryISO2(normalizedISO2);
        responseDTO.setCountryName(countryName);
        responseDTO.setSwiftCodes(swiftCodes);

        return ResponseEntity.ok(responseDTO);
    }


    @Transactional
    public AddSwiftCodeResponseDTO addSwiftCode(AddSwiftCodeRequestDTO requestDTO) {
        if (requestDTO.getCountryISO2() == null || requestDTO.getSwiftCode() == null || requestDTO.getBankName() == null) {
            throw new MissingRequiredFieldsException();
        }

        requestDTO.setCountryISO2(requestDTO.getCountryISO2().toUpperCase());

        if (bankRepository.existsById(requestDTO.getSwiftCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Bank with SWIFT code " + requestDTO.getSwiftCode() + " already exists");
        }


        Bank bank = swiftCodeMapper.toEntity(requestDTO);

        bankRepository.save(bank);

        return new AddSwiftCodeResponseDTO("SWIFT code added successfully.");
    }


    @Transactional
    public ResponseEntity<DeleteSwiftCodeResponseDTO> deleteSwiftCode(String swiftCode, String bankName, String countryISO2) {
        String normalizedCountryISO2 = countryISO2.toUpperCase();

        Bank bank = bankRepository.findBySwiftCodeAndBankNameAndCountryISO2(swiftCode, bankName, normalizedCountryISO2)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Bank with SWIFT code " + swiftCode + ", bankName " + bankName + " and countryISO2 " + normalizedCountryISO2 + " not found"));

        bankRepository.delete(bank);
        DeleteSwiftCodeResponseDTO responseDTO = new DeleteSwiftCodeResponseDTO("SWIFT code deleted successfully.");
        return ResponseEntity.ok(responseDTO);
    }
}
