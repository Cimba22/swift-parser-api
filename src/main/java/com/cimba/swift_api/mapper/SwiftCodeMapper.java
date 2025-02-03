package com.cimba.swift_api.mapper;

import com.cimba.swift_api.model.dto.request.AddSwiftCodeRequestDTO;
import com.cimba.swift_api.model.dto.response.BranchSwiftCodeResponseDTO;
import com.cimba.swift_api.model.dto.response.HeadquarterSwiftCodeResponseDTO;
import com.cimba.swift_api.model.entity.Bank;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SwiftCodeMapper {

    public BranchSwiftCodeResponseDTO toBranchDto(Bank code) {
        return new BranchSwiftCodeResponseDTO(
                code.getAddress(),
                code.getBankName(),
                code.getCountryISO2(),
                code.isHeadquarter(),
                code.getSwiftCode()
        );
    }

    public HeadquarterSwiftCodeResponseDTO toHeadquarterDto(Bank code, List<BranchSwiftCodeResponseDTO> branches) {
        return new HeadquarterSwiftCodeResponseDTO(
                code.getAddress(),
                code.getBankName(),
                code.getCountryISO2(),
                code.getCountryName(),
                code.isHeadquarter(),
                code.getSwiftCode(),
                branches
        );
    }

    public Bank toEntity(AddSwiftCodeRequestDTO requestDTO) {
        Bank bank = new Bank();
        bank.setSwiftCode(requestDTO.getSwiftCode());
        bank.setBankName(requestDTO.getBankName());
        bank.setAddress(requestDTO.getAddress());
        bank.setCountryISO2(requestDTO.getCountryISO2());
        bank.setCountryName(requestDTO.getCountryName());
        bank.setHeadquarter(requestDTO.isHeadquarter());
        return bank;
    }
}
