package com.library.repository;

import com.library.domain.Reader;
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
public class ReaderRepositoryTest {

    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void readerRepositoryTest() {
        //Given
        Reader reader = new Reader("Rafal", "Gryglas", LocalDate.now());
        //When
        readerRepository.save(reader);
        //Then
        Long id = reader.getId();
        Optional<Reader> reader1 = readerRepository.findById(id);
        Assert.assertEquals(id, reader1.get().getId());
        //CleanUp
        readerRepository.delete(id);
    }
}
