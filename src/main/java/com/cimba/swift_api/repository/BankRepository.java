package com.cimba.swift_api.repository;

import com.cimba.swift_api.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
    Optional<Bank> findBySwiftCode(String swiftCode);

    @Query(value = "SELECT * FROM banks b WHERE b.bank_name = :bankName AND b.countryiso2 = :countryISO2 AND b.swift_code LIKE CONCAT(:swiftCodePrefix, '%')", nativeQuery = true)
    List<Bank> findBranchesByBankNameAndCountry(@Param("bankName") String bankName,
                                                @Param("countryISO2") String countryISO2,
                                                @Param("swiftCodePrefix") String swiftCodePrefix);

    List<Bank> findByCountryISO2(String countryISO2);

    Optional<Bank> findBySwiftCodeAndBankNameAndCountryISO2(String swiftCode, String bankName, String countryISO2);
}
