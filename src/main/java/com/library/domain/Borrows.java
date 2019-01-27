package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "BORROWED_BOOKS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Borrows {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private Long borrowerId;

    @ManyToOne
    @JoinColumn(name = "readerId")
    @NotNull
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "booksListId")
    @NotNull
    private CopiesOfBook copiesOfBook;

    @Column(name = "Borrow_date")
    @NotNull
    private LocalDate rentalBook;

    @Column(name = "Return_date")
    private LocalDate returnBook;

    public Borrows(Reader reader, CopiesOfBook copiesOfBook, LocalDate rentalBook, LocalDate returnBook) {
        this.reader = reader;
        this.copiesOfBook = copiesOfBook;
        this.rentalBook = rentalBook;
        this.returnBook = returnBook;
    }
}
