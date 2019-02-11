package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "BooksList")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CopyOfBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long booksListId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "bookId")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", columnDefinition = "Enum('LOST','BORROWED','DESTROYED','FORRENT')")
    @NotNull
    private BookStatus bookStatus;

    public BookStatus setBookStatus(BookStatus bookStatus) {
        return this.bookStatus = bookStatus;
    }

    public CopyOfBook(Book book, BookStatus bookStatus) {
        this.book = book;
        this.bookStatus = bookStatus;
    }
}