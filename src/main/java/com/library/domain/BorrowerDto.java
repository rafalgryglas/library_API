package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowerDto {
    private Long borrowerId;
    private Long readerId;
    private Long booksListId;
    private LocalDate rentalBookDate;
    private LocalDate returnBookDate;
}
