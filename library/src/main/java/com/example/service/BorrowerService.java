package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BorrowerDto;
import com.example.dto.BorrowerResponseDto;

@Service
public interface BorrowerService {

    BorrowerResponseDto createBorrower(BorrowerDto dto);

    BorrowerResponseDto updateBorrower(Long id, BorrowerDto dto);

    BorrowerResponseDto getBorrower(Long id);

    List<BorrowerResponseDto> searchByName(String name);

    List<BorrowerResponseDto> getAllBorrowers();

    void deleteBorrower(Long id);
}
