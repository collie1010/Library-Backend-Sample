package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    
    List<Borrower> findByNameContainingIgnoreCase(String name);
    
    Optional<Borrower> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
