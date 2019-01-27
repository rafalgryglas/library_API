package com.library.service;

import com.library.domain.*;
import com.library.repository.BorrowsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowsServiceTestSuite {
    @Autowired
    private DbBorrows service;

    @MockBean
    private BorrowsRepository repository;

    @Test
    public void getAllTest() {
        //Given
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.now());
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        Book book1 = new Book(2L, "Second Book", "Author", LocalDate.of(2015, 11, 12));
        CopiesOfBook copies = new CopiesOfBook(1L, book, BookStatus.BORROWED);
        CopiesOfBook copies1 = new CopiesOfBook(4L, book1, BookStatus.BORROWED);
        List<Borrows> borrowsList = new ArrayList<>();
        Borrows borrows = new Borrows(1L, reader, copies, LocalDate.now(), null);
        Borrows borrows1 = new Borrows(2L, reader, copies1, LocalDate.of(2018, 12, 29),
                LocalDate.of(2019, 1, 15));
        borrowsList.add(borrows);
        borrowsList.add(borrows1);
        when(repository.findAll()).thenReturn(borrowsList);
        //When
        List<Borrows> resultList = service.getAll();
        //Then
        assertEquals(2, resultList.size());
    }

    @Test
    public void getBorrowerByIdTest() {
        //Given
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.now());
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copies = new CopiesOfBook(1L, book, BookStatus.BORROWED);
        Borrows borrows = new Borrows(1L, reader, copies, LocalDate.now(), null);
        when(repository.findByBorrowerId(1L)).thenReturn(Optional.ofNullable(borrows));
        //When
        Optional<Borrows> result = service.getBorrowerById(1L);
        //Then
        assertEquals(reader, result.get().getReader());
    }

    @Test
    public void saveBorrower() {
        //Given
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.now());
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copies = new CopiesOfBook(1L, book, BookStatus.BORROWED);
        Borrows borrows = new Borrows(1L, reader, copies, LocalDate.of(2018, 12, 12), LocalDate.now());
        when(repository.save(borrows)).thenReturn(borrows);
        //When
        Borrows result = service.save(borrows);
        //Then
        assertEquals("Gryglas", result.getReader().getLastname());
    }
}
