package com.example.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoanDto;
import com.example.dto.LoanResponseDto;
import com.example.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponseDto> create(@Validated @RequestBody LoanDto dto) {
        LoanResponseDto created = loanService.createLoan(dto);
        return ResponseEntity.created(URI.create("/api/loans/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoan(id));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDto>> getAll() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<List<LoanResponseDto>> getByBorrower(@PathVariable Long borrowerId) {
        return ResponseEntity.ok(loanService.getLoansByBorrower(borrowerId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<LoanResponseDto>> getByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(loanService.getLoansByBook(bookId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<LoanResponseDto>> getActive() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @GetMapping("/returned")
    public ResponseEntity<List<LoanResponseDto>> getReturned() {
        return ResponseEntity.ok(loanService.getReturnedLoans());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponseDto>> getOverdue() {
        return ResponseEntity.ok(loanService.getOverdueLoans());
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponseDto> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnLoan(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
