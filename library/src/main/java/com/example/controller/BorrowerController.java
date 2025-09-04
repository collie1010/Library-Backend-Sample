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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BorrowerDto;
import com.example.dto.BorrowerResponseDto;
import com.example.service.BorrowerService;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping
    public ResponseEntity<BorrowerResponseDto> create(@Validated @RequestBody BorrowerDto dto) {
        BorrowerResponseDto created = borrowerService.createBorrower(dto);
        return ResponseEntity.created(URI.create("/api/borrowers/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(borrowerService.getBorrower(id));
    }

    @GetMapping
    public ResponseEntity<List<BorrowerResponseDto>> search(@RequestParam(value = "q", required = false) String q) {
        if (q == null || q.isBlank()) {
            return ResponseEntity.ok(borrowerService.getAllBorrowers());
        }
        return ResponseEntity.ok(borrowerService.searchByName(q));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowerResponseDto> update(@PathVariable Long id, @Validated @RequestBody BorrowerDto dto) {
        return ResponseEntity.ok(borrowerService.updateBorrower(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.noContent().build();
    }
}
