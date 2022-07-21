package com.jonservices.conversionservice.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "conversions")
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "from_currency", nullable = false)
    private String from;
    @Column(name = "to_currency", nullable = false)
    private String to;
    @Column(name = "conversion_factor", nullable = false)
    private BigDecimal conversionFactor;
}
