package com.jonservices.bookservice.controller;

import com.jonservices.bookservice.data.dto.BookDTO;
import com.jonservices.bookservice.exceptions.ConversionProxyRequestException;
import com.jonservices.bookservice.proxy.ConversionProxy;
import com.jonservices.bookservice.response.Conversion;
import com.jonservices.bookservice.service.BookService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Endpoint")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private ConversionProxy proxy;

    @Autowired
    private Environment environment;

    @GetMapping
    @Operation(summary = "Find all registered books")
    @CircuitBreaker(name = "book-service")
    public ResponseEntity<List<BookDTO>> findAll() {
        final List<BookDTO> booksDTOList = service.findAll();
        addLinkToItself(booksDTOList);

        /* Log */
        System.out.println("BOOK-SERVICE -> PORT: " + environment.getProperty("local.server.port"));

        return ResponseEntity.ok(booksDTOList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a book by its id")
    @RateLimiter(name = "book-service")
    public ResponseEntity<BookDTO> findById(@PathVariable String id) {
        final BookDTO bookDTO = service.findById(id);
        addLinkToAll(bookDTO);

        /* Log */
        System.out.println("BOOK-SERVICE -> PORT: " + environment.getProperty("local.server.port"));

        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/{id}/{currency}")
    @Operation(summary = "Find a book by id and display its value in a specific currency")
    @Retry(name = "book-service")
    public ResponseEntity<BookDTO> findAndConvert(@PathVariable String id,
                                                  @PathVariable String currency) {
        final BookDTO bookDTO = service.findById(id);
        final Conversion conversion;

        try {
            conversion = proxy.convert(bookDTO.getPrice(), "USD", currency);
        } catch (Exception e) {
            throw new ConversionProxyRequestException("Invalid conversion request");
        }

        bookDTO.setCurrency(conversion.getTo());
        bookDTO.setPrice(conversion.getConvertedValue());

        /* Log */
        System.out.println("BOOK-SERVICE -> PORT: " + environment.getProperty("local.server.port"));

        addLinkToAll(bookDTO);

        return ResponseEntity.ok(bookDTO);
    }

    private void addLinkToItself(List<BookDTO> booksDTO) {
        booksDTO.forEach(bookDTO -> bookDTO.add(linkTo(methodOn(BookController.class)
                .findById(bookDTO.getId().toString()))
                .withSelfRel()));
    }

    private void addLinkToAll(BookDTO bookDTO) {
        bookDTO.add(linkTo(methodOn(BookController.class).findAll()).withRel("Books list"));
    }
}
