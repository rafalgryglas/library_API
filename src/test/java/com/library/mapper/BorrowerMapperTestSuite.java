package com.library.mapper;

import com.library.domain.*;
import com.library.service.DbCopy;
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
public class BorrowerMapperTestSuite {
    @Autowired
    private BorrowerMapper borrowerMapper;

    @MockBean
    private DbReader dbReader;
    @MockBean
    private DbCopy dbCopy;

    @Test
    public void mapToBorrowsTest() {
        //Given
        Reader reader = new Reader(1L,"Rafał","Gryglas",LocalDate.of(2018,12,13));
        Book book = new Book(2L, "Wiedzmin: Ostatnie życzenie", "Andrzej Sapkowski", LocalDate.of(1993, 1, 1));
        CopyOfBook copies = new CopyOfBook(3L,book,BookStatus.BORROWED);
        BorrowerDto borrowerDto = new BorrowerDto(1L,reader.getId(),copies.getBooksListId(),
                LocalDate.of(2018,12,30),LocalDate.now());
        when(dbReader.getReader(borrowerDto.getReaderId())).thenReturn(Optional.ofNullable(reader));
        when(dbCopy.getCopy(borrowerDto.getBooksListId())).thenReturn(Optional.ofNullable(copies));
        //When
        Long id = reader.getId();
        Borrower result = borrowerMapper.mapToBorrows(borrowerDto);
        //Then
        assertEquals(id, result.getReader().getId());
    }
    @Test
    public void mapToBorrowsDtoTest() {
        //Given
        Reader reader = new Reader(10L, "Rafal", "Gryglas", LocalDate.now());
        Book book1 = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copyOfBook = new CopyOfBook(1L, book1, BookStatus.LOST);
        Borrower borrower = new Borrower(1L, reader, copyOfBook, LocalDate.now(), null);
        //Then
        Long id = reader.getId();
        BorrowerDto borrowerDto = borrowerMapper.mapToBorrowsDto(borrower);
        //When
        assertEquals(id, borrowerDto.getReaderId());
    }

    @Test
    public void mapToBorrowsDtoListTest() {
        //Given
        Reader reader = new Reader(10L, "Rafal", "Gryglas", LocalDate.of(2019,1,1));
        Reader reader1 = new Reader(112L,"No","Name",LocalDate.of(2019,1,3));
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copyOfBook = new CopyOfBook(1L, book, BookStatus.BORROWED);
        CopyOfBook copyOfBook1 = new CopyOfBook(12L, book, BookStatus.FORRENT);
        Borrower borrower = new Borrower(2L, reader, copyOfBook, LocalDate.now(), null);
        Borrower borrower1 = new Borrower(1L,reader1, copyOfBook1,LocalDate.of(2019,1,18),LocalDate.now());
        List<Borrower> borrowerList = new ArrayList<>();
        borrowerList.add(borrower);
        borrowerList.add(borrower1);
        //When
        List<BorrowerDto> resultList = borrowerMapper.mapToBorrowsDtoList(borrowerList);
        //Then
        assertEquals(2,resultList.size());
        assertEquals(reader.getId(),resultList.get(0).getReaderId());
    }
}
