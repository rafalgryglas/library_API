package com.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "BOOKS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    private Long bookId;

    @NotNull
    @Column
    private String title;

    @NotNull
    @Column
    private String author;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "Published")
    private LocalDate dateOfPublication;

    public Book(String title, String author, LocalDate dateOfPublication) {
        this.title = title;
        this.author = author;
        this.dateOfPublication = dateOfPublication;
    }

}
