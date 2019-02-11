package com.library.repository;

import com.library.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowerRepositoryTest {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Test
    public void BorrowsRepositoryTest() {
        //Given
        Reader reader = new Reader("Rafal", "Gryglas", LocalDate.now());
        readerRepository.save(reader);
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        bookRepository.save(book);
        CopyOfBook copies = new CopyOfBook(book, BookStatus.BORROWED);
        copyRepository.save(copies);
        Borrower borrower = new Borrower(reader, copies, LocalDate.now(), null);
        //When
        borrowerRepository.save(borrower);
        //Then
        Long id = borrower.getBorrowerId();
        Optional<Borrower> getBorrower = borrowerRepository.findByBorrowerId(id);

        assertEquals(id, getBorrower.get().getBorrowerId());
        //CleanUp
        borrowerRepository.delete(borrower);
        copyRepository.delete(copies);
        readerRepository.delete(reader);
        bookRepository.delete(book);
    }
}
