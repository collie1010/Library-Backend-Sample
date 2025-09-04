package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.AuthorDto;
import com.example.dto.AuthorResponseDto;

@Service
public interface AuthorService {

    AuthorResponseDto createAuthor(AuthorDto dto);

    AuthorResponseDto updateAuthor(Long id, AuthorDto dto);

    AuthorResponseDto getAuthor(Long id);

    List<AuthorResponseDto> searchByName(String name);

    List<AuthorResponseDto> getAllAuthors();

    void deleteAuthor(Long id);
}
