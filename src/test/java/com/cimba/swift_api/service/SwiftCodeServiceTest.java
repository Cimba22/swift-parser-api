package com.cimba.swift_api.service;

import com.cimba.swift_api.exception.EntityNotFoundException;
import com.cimba.swift_api.exception.SwiftCodeNotFoundException;
import com.cimba.swift_api.model.dto.request.AddSwiftCodeRequestDTO;
import com.cimba.swift_api.model.dto.response.AddSwiftCodeResponseDTO;
import com.cimba.swift_api.model.dto.response.BranchSwiftCodeResponseDTO;
import com.cimba.swift_api.model.dto.response.CountrySwiftCodesResponseDTO;
import com.cimba.swift_api.model.dto.response.HeadquarterSwiftCodeResponseDTO;
import com.cimba.swift_api.model.entity.Bank;
import com.cimba.swift_api.mapper.SwiftCodeMapper;
import com.cimba.swift_api.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeServiceTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private SwiftCodeMapper swiftCodeMapper;

    @InjectMocks
    private SwiftCodeService swiftCodeService;


    @Test
    void testGetSwiftCodeDetails_headquarter() {

        Bank headquarter = new Bank();
        headquarter.setAddress("123 Main Street");
        headquarter.setBankName("Test Bank");
        headquarter.setCountryISO2("US");
        headquarter.setCountryName("United States");
        headquarter.setHeadquarter(true);
        headquarter.setSwiftCode("TESTUS33XXX");

        Bank branch1 = new Bank();
        branch1.setAddress("123 Main Street");
        branch1.setBankName("Test Bank");
        branch1.setCountryISO2("US");
        branch1.setCountryName("United States");
        branch1.setHeadquarter(false);
        branch1.setSwiftCode("TESTUS33ABC");

        Bank branch2 = new Bank();
        branch2.setAddress("123 Main Street");
        branch2.setBankName("Test Bank");
        branch2.setCountryISO2("US");
        branch2.setCountryName("United States");
        branch2.setHeadquarter(false);
        branch2.setSwiftCode("TESTUS33DEF");


        when(bankRepository.findBySwiftCode("TESTUS33XXX")).thenReturn(Optional.of(headquarter));
        when(bankRepository.findBranchesByBankNameAndCountry("Test Bank", "US", "TESTUS33"))
                .thenReturn(Arrays.asList(headquarter, branch1, branch2));


        BranchSwiftCodeResponseDTO branchDto1 = new BranchSwiftCodeResponseDTO(
                branch1.getAddress(),
                branch1.getBankName(),
                branch1.getCountryISO2(),
                branch1.isHeadquarter(),
                branch1.getSwiftCode()
        );

        BranchSwiftCodeResponseDTO branchDto2 = new BranchSwiftCodeResponseDTO(
                branch2.getAddress(),
                branch2.getBankName(),
                branch2.getCountryISO2(),
                branch2.isHeadquarter(),
                branch2.getSwiftCode()
        );


        when(swiftCodeMapper.toBranchDto(branch1)).thenReturn(branchDto1);
        when(swiftCodeMapper.toBranchDto(branch2)).thenReturn(branchDto2);

        ResponseEntity<?> responseEntity = swiftCodeService.getSwiftCodeDetails("TESTUS33XXX");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertInstanceOf(HeadquarterSwiftCodeResponseDTO.class, responseEntity.getBody());

        HeadquarterSwiftCodeResponseDTO responseDto = (HeadquarterSwiftCodeResponseDTO) responseEntity.getBody();
        assertEquals("TESTUS33XXX", responseDto.getSwiftCode());

        assertEquals(2, responseDto.getBranches().size());
        List<String> branchCodes = responseDto.getBranches().stream()
                .map(BranchSwiftCodeResponseDTO::getSwiftCode)
                .toList();
        assertTrue(branchCodes.contains("TESTUS33ABC"));
        assertTrue(branchCodes.contains("TESTUS33DEF"));
    }


    @Test
    void testGetSwiftCodeDetails_branch() {
        Bank branch = new Bank();
        branch.setAddress("123 Main Street");
        branch.setBankName("Test Bank");
        branch.setCountryISO2("US");
        branch.setCountryName("United States");
        branch.setHeadquarter(false);
        branch.setSwiftCode("TESTUS33ABC");

        when(bankRepository.findBySwiftCode("TESTUS33ABC")).thenReturn(Optional.of(branch));

        BranchSwiftCodeResponseDTO branchDto = new BranchSwiftCodeResponseDTO(
                branch.getAddress(),
                branch.getBankName(),
                branch.getCountryISO2(),
                branch.isHeadquarter(),
                branch.getSwiftCode()
        );


        when(swiftCodeMapper.toBranchDto(branch)).thenReturn(branchDto);

        ResponseEntity<?> responseEntity = swiftCodeService.getSwiftCodeDetails("TESTUS33ABC");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertInstanceOf(BranchSwiftCodeResponseDTO.class, responseEntity.getBody());

        BranchSwiftCodeResponseDTO responseDto = (BranchSwiftCodeResponseDTO) responseEntity.getBody();
        assertEquals("TESTUS33ABC", responseDto.getSwiftCode());
        assertFalse(responseDto.isHeadquarter());
    }


    @Test
    void testGetSwiftCodeDetails_notFound() {
        when(bankRepository.findBySwiftCode(anyString())).thenReturn(Optional.empty());

        SwiftCodeNotFoundException exception = assertThrows(SwiftCodeNotFoundException.class,
                () -> swiftCodeService.getSwiftCodeDetails("NONEXISTENT"));

        assertTrue(exception.getMessage().contains("Bank not found with SWIFT code: NONEXISTENT"));
    }


    @Test
    void testGetSwiftCodeDetailsByCountryISO2() {
        Bank bank1 = new Bank();
        bank1.setAddress("123 Main Street");
        bank1.setBankName("Test Bank");
        bank1.setCountryISO2("US");
        bank1.setCountryName("United States");
        bank1.setHeadquarter(true);
        bank1.setSwiftCode("TESTUS33XXX");

        Bank bank2 = new Bank();
        bank2.setAddress("456 Branch Road");
        bank2.setBankName("Test Bank");
        bank2.setCountryISO2("US");
        bank2.setCountryName("United States");
        bank2.setHeadquarter(false);
        bank2.setSwiftCode("TESTUS33ABC");

        when(bankRepository.findByCountryISO2("US")).thenReturn(Arrays.asList(bank1, bank2));

        BranchSwiftCodeResponseDTO dto1 = new BranchSwiftCodeResponseDTO(
                bank1.getAddress(),
                bank1.getBankName(),
                bank1.getCountryISO2(),
                bank1.isHeadquarter(),
                bank1.getSwiftCode()
        );


        BranchSwiftCodeResponseDTO dto2 = new BranchSwiftCodeResponseDTO(
                bank2.getAddress(),
                bank2.getBankName(),
                bank2.getCountryISO2(),
                bank2.isHeadquarter(),
                bank2.getSwiftCode()
        );


        when(swiftCodeMapper.toBranchDto(bank1)).thenReturn(dto1);
        when(swiftCodeMapper.toBranchDto(bank2)).thenReturn(dto2);

        ResponseEntity<?> responseEntity = swiftCodeService.getSwiftCodeDetailsByCountryISO2("us");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertInstanceOf(CountrySwiftCodesResponseDTO.class, responseEntity.getBody());

        CountrySwiftCodesResponseDTO responseDto = (CountrySwiftCodesResponseDTO) responseEntity.getBody();
        assertEquals("US", responseDto.getCountryISO2());
        assertEquals("United States", responseDto.getCountryName());
        assertEquals(2, responseDto.getSwiftCodes().size());
    }

    @Test
    void testAddSwiftCode_success() {
        AddSwiftCodeRequestDTO requestDTO = new AddSwiftCodeRequestDTO();
        requestDTO.setAddress("123 Main Street");
        requestDTO.setBankName("Test Bank");
        requestDTO.setCountryISO2("us");
        requestDTO.setCountryName("United States");
        requestDTO.setIsHeadquarter(true);
        requestDTO.setSwiftCode("TESTUS33XXX");

        when(bankRepository.existsById("TESTUS33XXX")).thenReturn(false);

        Bank bank = new Bank();
        bank.setAddress("123 Main Street");
        bank.setBankName("Test Bank");
        bank.setCountryISO2("US");
        bank.setCountryName("United States");
        bank.setHeadquarter(true);
        bank.setSwiftCode("TESTUS33XXX");

        when(swiftCodeMapper.toEntity(requestDTO)).thenReturn(bank);
        when(bankRepository.save(bank)).thenReturn(bank);

        AddSwiftCodeResponseDTO responseDTO = swiftCodeService.addSwiftCode(requestDTO);
        assertEquals("SWIFT code added successfully.", responseDTO.getMessage());
        verify(bankRepository, times(1)).save(bank);
    }

    @Test
    void testAddSwiftCode_conflict() {
        AddSwiftCodeRequestDTO requestDTO = new AddSwiftCodeRequestDTO();
        requestDTO.setSwiftCode("TESTUS33XXX");
        requestDTO.setCountryISO2("US");
        requestDTO.setBankName("Test Bank");

        when(bankRepository.existsById("TESTUS33XXX")).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> swiftCodeService.addSwiftCode(requestDTO));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }


    @Test
    void testDeleteSwiftCode_success() {
        Bank bank = new Bank();
        bank.setAddress("123 Main Street");
        bank.setBankName("Test Bank");
        bank.setCountryISO2("US");
        bank.setCountryName("United States");
        bank.setHeadquarter(true);
        bank.setSwiftCode("TESTUS33XXX");

        when(bankRepository.findBySwiftCodeAndBankNameAndCountryISO2("TESTUS33XXX", "Test Bank", "US"))
                .thenReturn(Optional.of(bank));

        ResponseEntity<?> response = swiftCodeService.deleteSwiftCode("TESTUS33XXX", "Test Bank", "US");
        assertEquals(200, response.getStatusCode().value());
        verify(bankRepository, times(1)).delete(bank);
    }


    @Test
    void testDeleteSwiftCode_notFound() {
        when(bankRepository.findBySwiftCodeAndBankNameAndCountryISO2(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> swiftCodeService.deleteSwiftCode("NONEXISTENT", "Test Bank", "US"));

        assertTrue(exception.getMessage().contains("not found"));
    }


}
