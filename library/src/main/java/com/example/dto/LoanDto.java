package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
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
public class LoanDto {

    @NotNull(message = "图书ID不能为空")
    private Long bookId;

    @NotNull(message = "借阅者ID不能为空")
    private Long borrowerId;

    private LocalDate loanDate;

    private LocalDate dueDate;
}
