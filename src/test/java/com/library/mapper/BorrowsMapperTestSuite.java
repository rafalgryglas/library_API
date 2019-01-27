package com.library.mapper;

import com.library.domain.*;
import com.library.service.DbCopies;
import com.library.service.DbReader;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowsMapperTestSuite {
    @Autowired
    private BorrowsMapper borrowsMapper;

    @MockBean
    private DbReader dbReader;
    @MockBean
    private DbCopies dbCopies;

    @Test
    public void mapToBorrowsTest() {
        //Given
        Reader reader = new Reader(1L,"Rafał","Gryglas",LocalDate.of(2018,12,13));
        Book book = new Book(2L, "Wiedzmin: Ostatnie życzenie", "Andrzej Sapkowski", LocalDate.of(1993, 1, 1));
        CopiesOfBook copies = new CopiesOfBook(3L,book,BookStatus.BORROWED);
        BorrowsDto borrowsDto = new BorrowsDto(1L,reader.getId(),copies.getBooksListId(),
                LocalDate.of(2018,12,30),LocalDate.now());
        when(dbReader.getReader(borrowsDto.getReaderId())).thenReturn(Optional.ofNullable(reader));
        when(dbCopies.getCopy(borrowsDto.getBooksListId())).thenReturn(Optional.ofNullable(copies));
        //When
        Long id = reader.getId();
        Borrows result = borrowsMapper.mapToBorrows(borrowsDto);
        //Then
        assertEquals(id, result.getReader().getId());
    }
    @Test
    public void mapToBorrowsDtoTest() {
        //Given
        Reader reader = new Reader(10L, "Rafal", "Gryglas", LocalDate.now());
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copiesOfBook = new CopiesOfBook(1L, book1, BookStatus.LOST);
        Borrows borrows = new Borrows(1L, reader, copiesOfBook, LocalDate.now(), null);
        //Then
        Long id = reader.getId();
        BorrowsDto borrowsDto = borrowsMapper.mapToBorrowsDto(borrows);
        //When
        assertEquals(id, borrowsDto.getReaderId());
    }

    @Test
    public void mapToBorrowsDtoListTest() {
        //Given
        Reader reader = new Reader(10L, "Rafal", "Gryglas", LocalDate.of(2019,1,1));
        Reader reader1 = new Reader(112L,"No","Name",LocalDate.of(2019,1,3));
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopiesOfBook copiesOfBook = new CopiesOfBook(1L, book, BookStatus.BORROWED);
        CopiesOfBook copiesOfBook1 = new CopiesOfBook(12L, book, BookStatus.FORRENT);
        Borrows borrows = new Borrows(2L, reader, copiesOfBook, LocalDate.now(), null);
        Borrows borrows1 = new Borrows(1L,reader1,copiesOfBook1,LocalDate.of(2019,1,18),LocalDate.now());
        List<Borrows> borrowsList = new ArrayList<>();
        borrowsList.add(borrows);
        borrowsList.add(borrows1);
        //When
        List<BorrowsDto> resultList = borrowsMapper.mapToBorrowsDtoList(borrowsList);
        //Then
        assertEquals(2,resultList.size());
        assertEquals(reader.getId(),resultList.get(0).getReaderId());
    }
}
