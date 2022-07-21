package com.jonservices.bookservice.converter;

import com.jonservices.bookservice.data.dto.BookDTO;
import com.jonservices.bookservice.data.model.Book;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapperConverter {
    private static ModelMapper mapper = new ModelMapper();

    public static BookDTO convertToDTO(Book book) {
        return mapper.map(book, BookDTO.class);
    }

    public static List<BookDTO> convertToDTOList(List<Book> booksList) {
        final List<BookDTO> booksDTOList = new ArrayList<>();
        booksList.forEach(book -> booksDTOList.add(convertToDTO(book)));
        return booksDTOList;
    }
}
