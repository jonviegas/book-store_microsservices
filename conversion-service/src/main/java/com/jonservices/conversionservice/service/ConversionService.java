package com.jonservices.conversionservice.service;

import com.jonservices.conversionservice.data.dto.ConversionDTO;
import com.jonservices.conversionservice.data.model.Conversion;
import com.jonservices.conversionservice.exceptions.InvalidCurrencyFormatException;
import com.jonservices.conversionservice.exceptions.InvalidValueFormatException;
import com.jonservices.conversionservice.exceptions.ResourceNotFoundException;
import com.jonservices.conversionservice.repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.jonservices.conversionservice.converter.ModelMapperConverter.convertToDTO;

@Service
public class ConversionService {

    @Autowired
    private ConversionRepository repository;

    public ConversionDTO convert(String value, String from, String to) {
        final BigDecimal valueToBeConverted = validateValue(value);
        final Conversion conversion = verifyIfExists(from, to);
        final ConversionDTO conversionDTO = convertToDTO(conversion);
        setConvertedValue(conversionDTO, valueToBeConverted);
        return conversionDTO;
    }

    private BigDecimal validateValue(String value) {
        if (!value.matches("^\\d+(?:\\.\\d*)?$"))
            throw new InvalidValueFormatException("Invalid value format: " + value);
        return BigDecimal.valueOf(Double.parseDouble(value));
    }

    private Conversion verifyIfExists(String from, String to) {
        if (from.length() != 3 || to.length() != 3)
            throw new InvalidCurrencyFormatException("Invalid currency format");
        return repository.findByFromAndTo(from.toUpperCase(), to.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Conversion between these currencies is not available"));
    }

    private void setConvertedValue(ConversionDTO conversionDTO, BigDecimal valueToBeConverted) {
        final BigDecimal convertedValue = valueToBeConverted
                .multiply(conversionDTO.getConversionFactor())
                .setScale(2, RoundingMode.CEILING);
        conversionDTO.setConvertedValue(convertedValue);
    }
}
