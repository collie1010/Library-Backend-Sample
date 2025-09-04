package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.BookResponseDto;
import com.example.dto.BorrowerResponseDto;
import com.example.dto.LoanDto;
import com.example.dto.LoanResponseDto;
import com.example.entity.Book;
import com.example.entity.Borrower;
import com.example.entity.Loan;
import com.example.exception.NotFoundException;
import com.example.repository.BookRepository;
import com.example.repository.BorrowerRepository;
import com.example.repository.LoanRepository;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, BorrowerRepository borrowerRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    @Override
    public LoanResponseDto createLoan(LoanDto dto) {
        // 验证图书是否存在且可用
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new NotFoundException("图书不存在"));
        
        if (!book.getAvailable()) {
            throw new IllegalArgumentException("图书不可借阅");
        }

        // 验证借阅者是否存在
        Borrower borrower = borrowerRepository.findById(dto.getBorrowerId())
                .orElseThrow(() -> new NotFoundException("借阅者不存在"));

        // 检查图书是否已被借出
        List<Loan> activeLoans = loanRepository.findActiveLoansByBook(dto.getBookId());
        if (!activeLoans.isEmpty()) {
            throw new IllegalArgumentException("图书已被借出");
        }

        // 设置借阅日期和到期日期
        LocalDate loanDate = dto.getLoanDate() != null ? dto.getLoanDate() : LocalDate.now();
        LocalDate dueDate = dto.getDueDate() != null ? dto.getDueDate() : loanDate.plusDays(14); // 默认14天

        Loan loan = Loan.builder()
                .book(book)
                .borrower(borrower)
                .loanDate(loanDate)
                .dueDate(dueDate)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        // 更新图书状态为不可用
        book.setAvailable(false);
        bookRepository.save(book);

        return convertToResponseDto(savedLoan);
    }

    @Override
    public LoanResponseDto getLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("借閱記錄不存在"));
        return convertToResponseDto(loan);
    }

    @Override
    public List<LoanResponseDto> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDto> getLoansByBorrower(Long borrowerId) {
        List<Loan> loans = loanRepository.findByBorrowerId(borrowerId);
        return loans.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDto> getLoansByBook(Long bookId) {
        List<Loan> loans = loanRepository.findByBookId(bookId);
        return loans.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDto> getActiveLoans() {
        List<Loan> loans = loanRepository.findByReturnedDateIsNull();
        return loans.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDto> getReturnedLoans() {
        List<Loan> loans = loanRepository.findByReturnedDateIsNotNull();
        return loans.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDto> getOverdueLoans() {
        List<Loan> loans = loanRepository.findOverdueLoans(LocalDate.now());
        return loans.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public LoanResponseDto returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("借閱記錄不存在"));

        if (loan.getReturnedDate() != null) {
            throw new IllegalArgumentException("圖書已經歸還");
        }

        loan.setReturnedDate(LocalDate.now());
        Loan savedLoan = loanRepository.save(loan);
        

        // 更新图书状态为可用
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return convertToResponseDto(savedLoan);
    }

    @Override
    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new NotFoundException("借閱記錄不存在");
        }
        loanRepository.deleteById(id);
    }

    private LoanResponseDto convertToResponseDto(Loan loan) {
        BookResponseDto bookDto = BookResponseDto.builder()
                .id(loan.getBook().getId())
                .title(loan.getBook().getTitle())
                .isbn(loan.getBook().getIsbn())
                .publishedYear(loan.getBook().getPublishedYear())
                .available(loan.getBook().getAvailable())
                .build();

        BorrowerResponseDto borrowerDto = BorrowerResponseDto.builder()
                .id(loan.getBorrower().getId())
                .name(loan.getBorrower().getName())
                .email(loan.getBorrower().getEmail())
                .createdAt(loan.getBorrower().getCreatedAt())
                .build();

        boolean isReturned = loan.getReturnedDate() != null;
        boolean isOverdue = !isReturned && loan.getDueDate().isBefore(LocalDate.now());

        return LoanResponseDto.builder()
                .id(loan.getId())
                .book(bookDto)
                .borrower(borrowerDto)
                .loanDate(loan.getLoanDate())
                .dueDate(loan.getDueDate())
                .returnedDate(loan.getReturnedDate())
                .isOverdue(isOverdue)
                .isReturned(isReturned)
                .build();
    }
}
