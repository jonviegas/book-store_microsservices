package com.jonservices.bookservice.service;

import com.jonservices.bookservice.data.dto.BookDTO;
import com.jonservices.bookservice.data.model.Book;
import com.jonservices.bookservice.exceptions.InvalidIDFormatException;
import com.jonservices.bookservice.exceptions.ResourceNotFoundException;
import com.jonservices.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jonservices.bookservice.converter.ModelMapperConverter.convertToDTO;
import static com.jonservices.bookservice.converter.ModelMapperConverter.convertToDTOList;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public List<BookDTO> findAll() {
        final List<Book> booksList = repository.findAll();
        return convertToDTOList(booksList);
    }

    public BookDTO findById(String stringId) {
        final long id = validateId(stringId);
        final Book book = verifyIfExists(id);
        return convertToDTO(book);
    }

    private Book verifyIfExists(long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book with ID " + id + " was not found"));
    }

    public long validateId(String id) {
        final boolean invalidId = !id.matches("[1-9]\\d*");
        if (invalidId)
            throw new InvalidIDFormatException("Invalid ID format");
        return Long.parseLong(id);
    }

}
