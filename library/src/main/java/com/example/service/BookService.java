package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BookDto;
import com.example.dto.BookResponseDto;

@Service
public interface BookService {

    BookResponseDto createBook(BookDto dto);

    BookResponseDto updateBook(Long id, BookDto dto);

    BookResponseDto getBook(Long id);

    List<BookResponseDto> searchByTitle(String q);

    void deleteBook(Long id);
}
