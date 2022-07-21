package com.jonservices.conversionservice.converter;

import com.jonservices.conversionservice.data.dto.ConversionDTO;
import com.jonservices.conversionservice.data.model.Conversion;
import org.modelmapper.ModelMapper;

public class ModelMapperConverter {
    private static ModelMapper mapper = new ModelMapper();

    public static ConversionDTO convertToDTO(Conversion conversion) {
        return mapper.map(conversion, ConversionDTO.class);
    }
}
