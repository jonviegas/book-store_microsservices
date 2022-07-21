package com.jonservices.bookservice.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "launch_date", nullable = false)
    private Date launchDate;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "title", nullable = false)
    private String title;
}
