package com.example.dto;

import jakarta.validation.constraints.NotBlank;
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
public class BookDto {

    @NotBlank
    private String title;
    private String isbn;
    private Integer publishedYear;
    private Long authorId; // 關聯 Author
    private Integer quantity; // 總庫存量
}
