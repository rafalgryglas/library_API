package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
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
public class ReaderMapperTestSuite {
    @Autowired
    private ReaderMapper readerMapper;

    @Test
    public void mapToReaderTest() {
        //Given
        ReaderDto readerDto = new ReaderDto(10L, "Rafal", "Gryglas", LocalDate.now());
        //When
        Reader reader = readerMapper.mapToReader(readerDto);
        //Then
        Assert.assertEquals("Rafal", reader.getName());
    }

    @Test
    public void mapToReaderDtoTest() {
        //Given
        Reader reader = new Reader(10L, "Rafal", "Gryglas", LocalDate.now());
        //Then
        ReaderDto readerDto = readerMapper.mapToReaderDto(reader);
        //When
        Assert.assertEquals("Gryglas", readerDto.getLastname());
    }

    @Test
    public void mapToReaderDtoListTest() {
        //Given
        Reader reader = new Reader(10L, "Rafal", "Gryglas", LocalDate.now());
        Reader reader2 = new Reader(11L, "Adam", "Kowalski", LocalDate.now());
        List<Reader> readersList = new ArrayList<>();
        readersList.add(reader);
        readersList.add(reader2);
        //Then
        List<ReaderDto> readerDtoList = readerMapper.mapToReaderDtoList(readersList);
        //When
        Assert.assertEquals(2, readerDtoList.size());
    }
}
