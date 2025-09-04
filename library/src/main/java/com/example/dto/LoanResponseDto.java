package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponseDto {

    private Long id;
    private BookResponseDto book;
    private BorrowerResponseDto borrower;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private boolean isOverdue;
    private boolean isReturned;
}
