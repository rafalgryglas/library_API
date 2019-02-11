package com.library.controller;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import com.library.mapper.ReaderMapper;
import com.library.service.DbReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReaderController.class)
public class ReaderControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private DbReader dbReader;

    @Test
    public void shouldFetchGetReaders() throws Exception {
        // Given
        List<ReaderDto> readerDtoList = new ArrayList<>();
        ReaderDto readerDto = new ReaderDto(1L, "Rafal", "Gryglas", LocalDate.of(2019, 1, 21));
        readerDtoList.add(readerDto);

        List<Reader> readerList = new ArrayList<>();
        when(readerMapper.mapToReaderDtoList(readerList)).thenReturn(readerDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/reader/getReaders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Rafal")))
                .andExpect(jsonPath("$[0].lastname", is("Gryglas")))
                .andExpect(jsonPath("$[0].dateCreateAccount", is("2019-01-21")));

    }

    @Test
    public void shouldFetchGetReader() throws Exception {
        //Given
        ReaderDto readerDto = new ReaderDto(1L, "Rafal", "Gryglas", LocalDate.of(2019, 1, 21));
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.of(2019, 1, 21));

        when(dbReader.getReader(1L)).thenReturn(Optional.ofNullable(reader));
        when(readerMapper.mapToReaderDto(any(Reader.class))).thenReturn(readerDto);
        //When & Then
        mockMvc.perform(get("/v1/library/reader/getReader?readerId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Rafal")))
                .andExpect(jsonPath("$.lastname", is("Gryglas")))
                .andExpect(jsonPath("$.dateCreateAccount", is("2019-01-21")));
    }

    @Test
    public void shouldDeleteReader() throws Exception {
        //Given
        Reader reader = new Reader(1L, "Rafal", "Gryglas", LocalDate.of(2019, 1, 21));
        dbReader.deleteReader(reader.getId());
        //When & Then
        mockMvc.perform(delete("/v1/library/reader/deleteReader?readerId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    public void shouldUpdateReader() throws Exception {
//        //Given
//        ReaderDto readerDto = new ReaderDto(1L, "Rafal", "Gryglas", LocalDate.of(2019, 1, 21));
//        when(readerMapper.mapToReaderDto(dbReader.saveReader(readerMapper.mapToReader(readerDto)))).thenReturn(readerDto);
//        //When & Then
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(readerDto);
//
//        mockMvc.perform(put("/v1/library/reader/updateReader")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.readerId", is(1)))
//                .andExpect(jsonPath("$.name", is("Rafal")))
//                .andExpect(jsonPath("$.lastname", is("Gryglas")))
//                .andExpect(jsonPath("$.dateCreateAccount", is("2019-01-21")));
//    }

//    @Test
//    public void shouldCreateReader() throws Exception {
//        //Given
//        Reader reader = new Reader();
//        ReaderDto readerDto = new ReaderDto(1L, "Rafal", "Gryglas", LocalDate.of(2019, 1, 21));
//        when(dbReader.saveReader(readerMapper.mapToReader(readerDto))).thenReturn(reader);
//
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(readerDto);
//        //When & Then
//        mockMvc.perform(post("/v1/library/reader/createReader")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(status().isOk());
//    }
}
