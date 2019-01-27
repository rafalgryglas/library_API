package com.library.service;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.CopiesOfBook;
import com.library.repository.CopiesRepository;
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
    private DbCopies service;

    @MockBean
    private CopiesRepository repository;

    @Test
    public void getAllCopiesTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        List<CopiesOfBook> copies = new ArrayList<>();
        copies.add(new CopiesOfBook(1L, book, BookStatus.DESTROYED));
        copies.add(new CopiesOfBook(2L, book, BookStatus.FORRENT));
        when(repository.findAll()).thenReturn(copies);
        //When
        List<CopiesOfBook> resultList = service.getAllCopies();
        //Then
        assertEquals(2, resultList.size());
    }

    @Test
    public void getCopieTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copies = new CopiesOfBook(1L, book, BookStatus.FORRENT);
        when(repository.findByBooksListId(1L)).thenReturn(Optional.ofNullable(copies));
        //When
        Optional<CopiesOfBook> result = service.getCopy(1L);
        //Then
        assertEquals("Pan Tadeusz", result.get().getBook().getTitle());
    }

    @Test
    public void getCopiesByStatusAndTitleIdTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        List<CopiesOfBook> copies = new ArrayList<>();
        copies.add(new CopiesOfBook(3L, book, BookStatus.BORROWED));
        copies.add(new CopiesOfBook(4L, book, BookStatus.BORROWED));
        when(repository.findBy(BookStatus.BORROWED, 1L)).thenReturn(copies);
        //When
        List<CopiesOfBook> resultList = service.getCopiesByStatusAndTitleId(BookStatus.BORROWED, 1L);
        System.out.println(resultList);
        //Then
        assertEquals(2, resultList.size());
    }

    @Test
    public void saveCopie() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copies = new CopiesOfBook(1L, book, BookStatus.FORRENT);
        when(repository.save(copies)).thenReturn(copies);
        //When
        CopiesOfBook result = service.saveCopy(copies);
        //Then
        assertEquals("Pan Tadeusz", result.getBook().getTitle());
    }
}
