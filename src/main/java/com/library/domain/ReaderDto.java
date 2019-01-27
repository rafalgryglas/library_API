package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReaderDto {
    private Long id;
    private String name;
    private String lastname;
    private LocalDate dateCreateAccount;
}
