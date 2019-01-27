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
public class BorrowsRepositoryTest {
    @Autowired
    private BorrowsRepository borrowsRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CopiesRepository copiesRepository;

    @Test
    public void BorrowsRepositoryTest() {
        //Given
        Reader reader = new Reader("Rafal", "Gryglas", LocalDate.now());
        readerRepository.save(reader);
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        bookRepository.save(book);
        CopiesOfBook copies = new CopiesOfBook(book, BookStatus.BORROWED);
        copiesRepository.save(copies);
        Borrows borrows = new Borrows(reader, copies, LocalDate.now(), null);
        //When
        borrowsRepository.save(borrows);
        //Then
        Long id = borrows.getBorrowerId();
        Optional<Borrows> getBorrower = borrowsRepository.findByBorrowerId(id);
        List<Borrows> getBorrowsList = borrowsRepository.findAll();

        assertEquals(id, getBorrower.get().getBorrowerId());
        assertEquals(1, getBorrowsList.size());
        //CleanUp
        borrowsRepository.delete(borrows);
        copiesRepository.delete(copies);
        readerRepository.delete(reader);
        bookRepository.delete(book);
    }
}
