package com.library.service;

import com.library.domain.Reader;
import com.library.repository.ReaderRepository;
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

public class ReaderServiceTestSuite {
    @Autowired
    private DbReader service;
    @MockBean
    private ReaderRepository repository;

    @Test
    public void getReadersTest() {
        //Given
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader(1L, "Rafal", "Gryglas", LocalDate.now()));
        readers.add(new Reader(2L, "No", "Name", LocalDate.now()));
        when(repository.findAll()).thenReturn(readers);
        //When
        List<Reader> resultList = service.getReaders();
        //Then
        assertEquals(2, resultList.size());
    }

    @Test
    public void getReaderTest() {
        //Given
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.now());
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(reader));
        //When
        Optional<Reader> result = service.getReader(1L);
        //Then
        assertEquals("Rafal", result.get().getName());
    }

    @Test
    public void saveReaderTest() {
        //Given
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.now());
        when(repository.save(reader)).thenReturn(reader);
        //When
        Reader result = service.saveReader(reader);
        //Then
        assertEquals(LocalDate.of(2019, 1, 21), result.getDateCreateAccount());
    }
}
