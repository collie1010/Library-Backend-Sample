package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    List<Loan> findByBorrowerId(Long borrowerId);
    
    List<Loan> findByBookId(Long bookId);
    
    List<Loan> findByReturnedDateIsNull();
    
    List<Loan> findByReturnedDateIsNotNull();
    
    @Query("SELECT l FROM Loan l WHERE l.dueDate < :currentDate AND l.returnedDate IS NULL")
    List<Loan> findOverdueLoans(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT l FROM Loan l WHERE l.borrower.id = :borrowerId AND l.returnedDate IS NULL")
    List<Loan> findActiveLoansByBorrower(@Param("borrowerId") Long borrowerId);
    
    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId AND l.returnedDate IS NULL")
    List<Loan> findActiveLoansByBook(@Param("bookId") Long bookId);
}
