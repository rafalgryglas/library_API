package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookStatus;
import com.library.domain.CopiesOfBook;
import com.library.domain.CopiesOfBookDto;
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
public class CopiesMapperTestSuite {
    @Autowired
    private CopiesMapper copiesMapper;

    @MockBean
    private DbBook dbBook;

    @Test
    public void mapToCopiesTest() {
        //Given
        Book book = new Book(2L, "Wiedzmin: Ostatnie życzenie", "Andrzej Sapkowski", LocalDate.of(1993, 1, 1));
        CopiesOfBookDto copiesOfBookDto = new CopiesOfBookDto(1L, book.getBookId(), BookStatus.BORROWED);
        when(dbBook.getBook(copiesOfBookDto.getBookId())).thenReturn(Optional.ofNullable(book));
        //Then
        CopiesOfBook copiesOfBook = copiesMapper.mapToCopies(copiesOfBookDto);
        //When
        Assert.assertEquals("Andrzej Sapkowski", copiesOfBook.getBook().getAuthor());
    }

    @Test
    public void mapToCopiesDtoTest() {
        //Given
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copiesOfBook = new CopiesOfBook(1L, book1, BookStatus.LOST);
        //Then
        CopiesOfBookDto copiesOfBookDto = copiesMapper.mapToCopiesDto(copiesOfBook);
        //When
        Assert.assertEquals(BookStatus.LOST, copiesOfBookDto.getBookStatus());
    }

    @Test
    public void mapToCopiesDtoListTest() {
        //Given
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copiesOfBook = new CopiesOfBook(1L, book1, BookStatus.LOST);
        CopiesOfBook copiesOfBook1 = new CopiesOfBook(2L, book1, BookStatus.FORRENT);
        List<CopiesOfBook> copiesList = new ArrayList<>();
        copiesList.add(copiesOfBook);
        copiesList.add(copiesOfBook1);
        //Then
        List<CopiesOfBookDto> copiesOfBookDtoList = copiesMapper.mapToCopiesDtoList(copiesList);
        //When
        Assert.assertEquals(2, copiesOfBookDtoList.size());
        Assert.assertEquals(BookStatus.LOST, copiesOfBookDtoList.get(0).getBookStatus());
        Assert.assertEquals(book1.getBookId(), copiesOfBookDtoList.get(1).getBookId());
    }
}
