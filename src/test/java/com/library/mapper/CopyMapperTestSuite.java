package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.CopyOfBook;
import com.library.domain.CopyOfBookDto;
import com.library.service.DbBook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CopyMapperTestSuite {
    @Autowired
    private CopyMapper copyMapper;

    @MockBean
    private DbBook dbBook;

    @Test
    public void mapToCopiesTest() {
        //Given
        Book book = new Book(2L, "Wiedzmin: Ostatnie życzenie", "Andrzej Sapkowski", LocalDate.of(1993, 1, 1));
        CopyOfBookDto copyOfBookDto = new CopyOfBookDto(1L, book.getBookId(), BookStatus.BORROWED);
        when(dbBook.getBook(copyOfBookDto.getBookId())).thenReturn(Optional.ofNullable(book));
        //Then
        CopyOfBook copyOfBook = copyMapper.mapToCopy(copyOfBookDto);
        //When
        Assert.assertEquals("Andrzej Sapkowski", copyOfBook.getBook().getAuthor());
    }

    @Test
    public void mapToCopiesDtoTest() {
        //Given
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copyOfBook = new CopyOfBook(1L, book1, BookStatus.LOST);
        //Then
        CopyOfBookDto copyOfBookDto = copyMapper.mapToCopyDto(copyOfBook);
        //When
        Assert.assertEquals(BookStatus.LOST, copyOfBookDto.getBookStatus());
    }

    @Test
    public void mapToCopiesDtoListTest() {
        //Given
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copyOfBook = new CopyOfBook(1L, book1, BookStatus.LOST);
        CopyOfBook copyOfBook1 = new CopyOfBook(2L, book1, BookStatus.FORRENT);
        List<CopyOfBook> copiesList = new ArrayList<>();
        copiesList.add(copyOfBook);
        copiesList.add(copyOfBook1);
        //Then
        List<CopyOfBookDto> copyOfBookDtoList = copyMapper.mapToCopyDtoList(copiesList);
        //When
        Assert.assertEquals(2, copyOfBookDtoList.size());
        Assert.assertEquals(BookStatus.LOST, copyOfBookDtoList.get(0).getBookStatus());
        Assert.assertEquals(book1.getBookId(), copyOfBookDtoList.get(1).getBookId());
    }
}
