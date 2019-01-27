package com.library.repository;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.CopiesOfBook;
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
public class CopiesRepositoryTest {
    @Autowired
    private CopiesRepository copiesRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void copiesRepositoryTest() {

        //Given
        Book book = new Book("Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        bookRepository.save(book);
        CopiesOfBook copies1 = new CopiesOfBook(book, BookStatus.BORROWED);
        CopiesOfBook copies2 = new CopiesOfBook(book, BookStatus.FORRENT);
        //When
        copiesRepository.save(copies1);
        copiesRepository.save(copies2);
        //Then
        Long id = copies1.getBooksListId();
        Long idBook = book.getBookId();
        Optional<CopiesOfBook> getCopies = copiesRepository.findByBooksListId(id);
        List<CopiesOfBook> findAll = copiesRepository.findAll();

        Assert.assertEquals(id, getCopies.get().getBooksListId());
        Assert.assertEquals(2, findAll.size());
        //CleanUp
        copiesRepository.delete(copies1);
        copiesRepository.delete(copies2);
        bookRepository.delete(book);
    }
}
