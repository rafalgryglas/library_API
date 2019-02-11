package com.library.repository;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.CopyOfBook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CopyRepositoryTest {
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void copiesRepositoryTest() {

        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        bookRepository.save(book);
        CopyOfBook copies1 = new CopyOfBook(book, BookStatus.FORRENT);
        //When
        copyRepository.save(copies1);
        //Then
        Long id = copies1.getBooksListId();
        Long bookId = book.getBookId();
        Optional<CopyOfBook> getCopies = copyRepository.findByBooksListId(id);
        List<CopyOfBook> findAll = copyRepository.findAll();

        Assert.assertEquals(id, getCopies.get().getBooksListId());
        //Assert.assertEquals(1, findAll.size());
        //CleanUp
        copyRepository.delete(id);
        bookRepository.delete(bookId);
    }
}
