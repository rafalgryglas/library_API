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
public class Borrower {
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
    private CopyOfBook copyOfBook;

    @Column(name = "Borrow_date")
    private LocalDate rentalBookDate;

    @Column(name = "Return_date")
    private LocalDate returnBookDate;

    public Borrower(Reader reader, CopyOfBook copyOfBook, LocalDate rentalBookDate, LocalDate returnBookDate) {
        this.reader = reader;
        this.copyOfBook = copyOfBook;
        this.rentalBookDate = rentalBookDate;
        this.returnBookDate = returnBookDate;
    }
}
