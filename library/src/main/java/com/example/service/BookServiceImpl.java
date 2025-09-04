package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.BookDto;
import com.example.dto.BookResponseDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.NotFoundException;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookResponseDto createBook(BookDto dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));
        Book book = Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publishedYear(dto.getPublishedYear())
                .author(author)
                .available(true)
                .build();
        Book savedBook = bookRepository.save(book);
        return convertToResponseDto(savedBook);
    }

    @Override
    public BookResponseDto updateBook(Long id, BookDto dto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublishedYear(dto.getPublishedYear());
        if (dto.getAuthorId() != null) {
            Author a = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new NotFoundException("Author not found"));
            book.setAuthor(a);
        }
        Book savedBook = bookRepository.save(book);
        return convertToResponseDto(savedBook);
    }

    @Override
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        return convertToResponseDto(book);
    }

    @Override
    public List<BookResponseDto> searchByTitle(String q) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(q);
        return books.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookResponseDto convertToResponseDto(Book book) {
        BookResponseDto.AuthorResponseDto authorDto = null;
        if (book.getAuthor() != null) {
            authorDto = BookResponseDto.AuthorResponseDto.builder()
                    .id(book.getAuthor().getId())
                    .name(book.getAuthor().getName())
                    .bio(book.getAuthor().getBio())
                    .build();
        }
        
        return BookResponseDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publishedYear(book.getPublishedYear())
                .available(book.getAvailable())
                .author(authorDto)
                .build();
    }
}
