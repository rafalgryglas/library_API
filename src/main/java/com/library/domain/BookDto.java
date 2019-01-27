package com.library.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Long bookId;
    private String title;
    private String author;
    private LocalDate dateOfPublication;

}
