package com.library.controller;

import com.google.gson.Gson;
import com.library.domain.*;
import com.library.mapper.CopyMapper;
import com.library.service.DbCopy;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CopyController.class)
public class CopyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbCopy dbCopy;

    @MockBean
    private CopyMapper copyMapper;

    @Test
    public void shouldFetchCopiesList() throws Exception {
        // Given
        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        List<CopyOfBookDto> copiesDtoList = new ArrayList<>();
        copiesDtoList.add(new CopyOfBookDto(1L, bookDto.getBookId(), BookStatus.FORRENT));

        List<CopyOfBook> copyOfBookList = new ArrayList<>();
        when(copyMapper.mapToCopyDtoList(copyOfBookList)).thenReturn(copiesDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/copiesList/getListCopies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].booksListId", is(1)))
                .andExpect(jsonPath("$[0].bookId", is(1)))
                .andExpect(jsonPath("$[0].bookStatus", is("FORRENT")));

    }

    @Test
    public void shouldFetchCopyOfBook() throws Exception {
        //Given
        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));

        CopyOfBook copyOfBook = new CopyOfBook(1L, book, BookStatus.FORRENT);
        CopyOfBookDto copyOfBookDto = new CopyOfBookDto(1L, bookDto.getBookId(), BookStatus.FORRENT);
        when(dbCopy.getCopy(1L)).thenReturn(Optional.ofNullable(copyOfBook));
        when(copyMapper.mapToCopyDto(any(CopyOfBook.class))).thenReturn(copyOfBookDto);
        //When & Then
        mockMvc.perform(get("/v1/library/copiesList/getCopyOfBook?copyId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.booksListId", is(1)))
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.bookStatus", is("FORRENT")));
    }

    @Test
    public void shouldDeleteCopy() throws Exception {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBook copyOfBook = new CopyOfBook(1L, book, BookStatus.FORRENT);

        dbCopy.deleteCopyOfBook(copyOfBook.getBooksListId());
        //When & Then
        mockMvc.perform(delete("/v1/library/copiesList/deleteCopy?copyId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateCopy() throws Exception {
        //Given
        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBookDto copyOfBookDto = new CopyOfBookDto(1L, bookDto.getBookId(), BookStatus.FORRENT);
        when(copyMapper.mapToCopyDto(dbCopy.saveCopy(copyMapper.mapToCopy(copyOfBookDto)))).thenReturn(copyOfBookDto);
        //When & Then
        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyOfBookDto);

        mockMvc.perform(put("/v1/library/copiesList/updateCopyOfBook")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.booksListId", is(1)))
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.bookStatus", is("FORRENT")));
    }

    @Test
    public void shouldCreateCopy() throws Exception {
        //Given
        CopyOfBook copyOfBook = new CopyOfBook();
        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        CopyOfBookDto copyOfBookDto = new CopyOfBookDto(1L, bookDto.getBookId(), BookStatus.FORRENT);
        when(dbCopy.saveCopy(copyMapper.mapToCopy(copyOfBookDto))).thenReturn(copyOfBook);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyOfBookDto);
        //When & Then
        mockMvc.perform(post("/v1/library/copiesList/createCopyOfBook")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}
