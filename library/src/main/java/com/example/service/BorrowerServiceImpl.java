package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.BorrowerDto;
import com.example.dto.BorrowerResponseDto;
import com.example.entity.Borrower;
import com.example.exception.NotFoundException;
import com.example.repository.BorrowerRepository;

@Service
@Transactional
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public BorrowerResponseDto createBorrower(BorrowerDto dto) {
        // 检查邮箱是否已存在
        if (dto.getEmail() != null && borrowerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("郵箱已存在");
        }
        
        Borrower borrower = Borrower.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        Borrower savedBorrower = borrowerRepository.save(borrower);
        return convertToResponseDto(savedBorrower);
    }

    @Override
    public BorrowerResponseDto updateBorrower(Long id, BorrowerDto dto) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("借閱者不存在"));
        
        // 检查邮箱是否已被其他借阅者使用
        if (dto.getEmail() != null && !dto.getEmail().equals(borrower.getEmail())) {
            if (borrowerRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("郵箱已被其他借閱者使用");
            }
        }
        
        borrower.setName(dto.getName());
        borrower.setEmail(dto.getEmail());
        Borrower savedBorrower = borrowerRepository.save(borrower);
        return convertToResponseDto(savedBorrower);
    }

    @Override
    public BorrowerResponseDto getBorrower(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("借閱者不存在"));
        return convertToResponseDto(borrower);
    }

    @Override
    public List<BorrowerResponseDto> searchByName(String name) {
        List<Borrower> borrowers = borrowerRepository.findByNameContainingIgnoreCase(name);
        return borrowers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowerResponseDto> getAllBorrowers() {
        List<Borrower> borrowers = borrowerRepository.findAll();
        return borrowers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id)) {
            throw new NotFoundException("借閱者不存在");
        }
        borrowerRepository.deleteById(id);
    }

    private BorrowerResponseDto convertToResponseDto(Borrower borrower) {
        return BorrowerResponseDto.builder()
                .id(borrower.getId())
                .name(borrower.getName())
                .email(borrower.getEmail())
                .createdAt(borrower.getCreatedAt())
                .build();
    }
}
