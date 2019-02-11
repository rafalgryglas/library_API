package com.library.service;

import com.library.domain.*;
import com.library.mapper.BorrowerMapper;
import com.library.repository.BorrowerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowerServiceTestSuite {
    @Autowired
    private DbBorrower dbBorrower;

    @MockBean
    private BorrowerRepository borrowerRepository;

    @MockBean
    private BorrowerMapper borrowerMapper;

    @MockBean
    private DbCopy dbCopy;

    @Test
    public void borrowBookTest() {
        //Given

        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.now());
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copy = new CopyOfBook(1L, book, BookStatus.FORRENT);
        Borrower borrower = new Borrower(1L, reader, copy, null, null);
        BorrowerDto borrowerDto = new BorrowerDto(1L, reader.getId(), copy.getBooksListId(), null, null);
        when(borrowerMapper.mapToBorrows(borrowerDto)).thenReturn(borrower);
        when(dbCopy.getCopy(borrower.getCopyOfBook().getBooksListId())).thenReturn(ofNullable(copy));
        //When
        boolean result = dbBorrower.borrowBook(borrowerDto);
        BookStatus status = copy.getBookStatus();
        LocalDate today = borrower.getRentalBookDate();
        //Then
        assertTrue(result);
        assertEquals(BookStatus.BORROWED, status);
        assertEquals(LocalDate.now(), today);
    }

    @Test
    public void returnBook() {
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copy = new CopyOfBook(1L, book, BookStatus.BORROWED);
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.of(2019, 2, 1));
        Borrower borrower = new Borrower(1L, reader, copy, LocalDate.of(2019, 2, 1), null);
        when(dbCopy.getCopy(copy.getBooksListId())).thenReturn(ofNullable(copy));
        when(borrowerRepository.findByCopyOfBook(copy)).thenReturn(borrower);
        //when
        boolean result = dbBorrower.returnBook(1L);
        BookStatus status = copy.getBookStatus();
        LocalDate today = borrower.getReturnBookDate();
        //Then
        assertTrue(result);
        assertEquals(BookStatus.FORRENT, status);
        assertEquals(LocalDate.now(), today);
    }
}
