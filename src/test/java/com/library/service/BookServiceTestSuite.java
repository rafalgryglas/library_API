package com.library.service;

import com.library.domain.Book;
import com.library.repository.BookRepository;
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

public class BookServiceTestSuite {
    @Autowired
    private DbBook service;

    @MockBean
    private BookRepository repository;

    @Test
    public void getAllBooksTest() {
        //Given
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28)));
        books.add(new Book(2L, "Second Book", "Author", LocalDate.of(2015, 11, 12)));
        when(repository.findAll()).thenReturn(books);
        //When
        List<Book> result = service.getAllBooks();
        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void getBookTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        when(repository.findByBookId(1L)).thenReturn(Optional.ofNullable(book));
        //When
        Optional<Book> resultBook = service.getBook(1L);
        //Then
        assertEquals("Adam Mickiewicz", resultBook.get().getAuthor());
    }

    @Test
    public void saveBookTest() {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        when(repository.save(book)).thenReturn(book);
        //When
        Book bookResult = service.saveBook(book);
        //Then
        assertEquals("Pan Tadeusz", bookResult.getTitle());
    }
}
