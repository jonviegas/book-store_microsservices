package com.jonservices.conversionservice.controller;

import com.jonservices.conversionservice.data.dto.ConversionDTO;
import com.jonservices.conversionservice.service.ConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversion")
@Tag(name = "Conversion Endpoint")
public class ConversionController {
    @Autowired
    private ConversionService service;

    @Autowired
    private Environment environment;

    @GetMapping("/{value}/{from}/{to}")
    @Operation(summary = "Converts a value between two currencies")
    public ResponseEntity<ConversionDTO> convert(@PathVariable String value,
                                                 @PathVariable String from,
                                                 @PathVariable String to) {
        final ConversionDTO conversionDTO = service.convert(value, from, to);

        /* Log */
        System.out.println("CONVERSION-SERVICE -> PORT: " + environment.getProperty("local.server.port"));

        return ResponseEntity.ok(conversionDTO);
    }
}
