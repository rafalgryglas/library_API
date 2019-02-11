package com.library.service;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.CopyOfBook;
import com.library.repository.CopyRepository;
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
public class CopiesServiceTestSuit {
    @Autowired
    private DbCopy service;

    @MockBean
    private CopyRepository repository;

    @Test
    public void getAllCopiesTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        List<CopyOfBook> copies = new ArrayList<>();
        copies.add(new CopyOfBook(1L, book, BookStatus.DESTROYED));
        copies.add(new CopyOfBook(2L, book, BookStatus.FORRENT));
        when(repository.findAll()).thenReturn(copies);
        //When
        List<CopyOfBook> resultList = service.getAllCopies();
        //Then
        assertEquals(2, resultList.size());
    }

    @Test
    public void getCopieTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copies = new CopyOfBook(1L, book, BookStatus.FORRENT);
        when(repository.findByBooksListId(1L)).thenReturn(Optional.ofNullable(copies));
        //When
        Optional<CopyOfBook> result = service.getCopy(1L);
        //Then
        assertEquals("Pan Tadeusz", result.get().getBook().getTitle());
    }

    @Test
    public void saveCopie() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copies = new CopyOfBook(1L, book, BookStatus.FORRENT);
        when(repository.save(copies)).thenReturn(copies);
        //When
        CopyOfBook result = service.saveCopy(copies);
        //Then
        assertEquals("Pan Tadeusz", result.getBook().getTitle());
    }
}
