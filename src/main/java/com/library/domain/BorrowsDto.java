package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowsDto {
    private Long borrowerId;
    private Long readerId;
    private Long booksListId;
    private LocalDate rentalBook;
    private LocalDate returnBook;
}
