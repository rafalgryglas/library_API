package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMapperTestSuite {
    @Autowired
    private BookMapper bookMapper;

    @Test
    public void mapToBookTest() {
        //Given
        BookDto bookDto = new BookDto(5L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        //Then
        Book book = bookMapper.mapToBook(bookDto);
        //When
        Assert.assertEquals("Pan Tadeusz", book.getTitle());
    }

    @Test
    public void mapToBookDtoTest() {
        //Given
        Book book = new Book(5L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        //Then
        BookDto bookDto = bookMapper.mapToBookDto(book);
        //When
        Assert.assertEquals(1834, bookDto.getDateOfPublication().getYear());
    }

    @Test
    public void mapToBookDtoListTest() {
        //Given
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        Book book2 = new Book(2L, "Wiedzmin: Ostatnie życzenie", "Andrzej Sapkowski", LocalDate.of(1993, 1, 1));
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        //Then
        List<BookDto> bookDtoList = bookMapper.mapToBookDtoList(bookList);
        //When
        Assert.assertEquals(2, bookDtoList.size());
    }
}
