package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.LoanDto;
import com.example.dto.LoanResponseDto;

@Service
public interface LoanService {

    LoanResponseDto createLoan(LoanDto dto);

    LoanResponseDto getLoan(Long id);

    List<LoanResponseDto> getAllLoans();

    List<LoanResponseDto> getLoansByBorrower(Long borrowerId);

    List<LoanResponseDto> getLoansByBook(Long bookId);

    List<LoanResponseDto> getActiveLoans();

    List<LoanResponseDto> getReturnedLoans();

    List<LoanResponseDto> getOverdueLoans();

    LoanResponseDto returnLoan(Long id);

    void deleteLoan(Long id);
}
