package com.library.repository;

import com.library.domain.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void bookRepositoryTest() {
        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        //When
        bookRepository.save(book);
        Long id = book.getBookId();
        Optional<Book> getBook = bookRepository.findByBookId(id);
        //Then
        Assert.assertEquals(id, getBook.get().getBookId());
        //CleanUp
        bookRepository.delete(id);
    }
}
