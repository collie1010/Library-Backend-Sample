package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.AuthorDto;
import com.example.dto.AuthorResponseDto;
import com.example.entity.Author;
import com.example.exception.NotFoundException;
import com.example.repository.AuthorRepository;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorDto dto) {
        Author author = Author.builder()
                .name(dto.getName())
                .bio(dto.getBio())
                .build();
        Author savedAuthor = authorRepository.save(author);
        return convertToResponseDto(savedAuthor);
    }

    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
        author.setName(dto.getName());
        author.setBio(dto.getBio());
        Author savedAuthor = authorRepository.save(author);
        return convertToResponseDto(savedAuthor);
    }

    @Override
    public AuthorResponseDto getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
        return convertToResponseDto(author);
    }

    @Override
    public List<AuthorResponseDto> searchByName(String name) {
        List<Author> authors = authorRepository.findByNameContainingIgnoreCase(name);
        return authors.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorResponseDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new NotFoundException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    private AuthorResponseDto convertToResponseDto(Author author) {
        return AuthorResponseDto.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .build();
    }
}
