package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CopyOfBookDto {
    private Long booksListId;
    private Long bookId;
    private BookStatus bookStatus;
}
