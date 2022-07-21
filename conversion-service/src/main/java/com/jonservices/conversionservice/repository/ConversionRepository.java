package com.jonservices.conversionservice.repository;

import com.jonservices.conversionservice.data.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    Optional<Conversion> findByFromAndTo(String from, String to);
}
