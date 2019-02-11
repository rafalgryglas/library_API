package com.library.controller;

import com.google.gson.Gson;
import com.library.domain.Book;
import com.library.domain.BookDto;
import com.library.mapper.BookMapper;
import com.library.service.DbBook;
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
@WebMvcTest(BookController.class)
public class BookControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private DbBook dbBook;

    @Test
    public void shouldFetchGetBooks() throws Exception {
        // Given
        List<BookDto> bookDtoList = new ArrayList<>();
        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        bookDtoList.add(bookDto);

        List<Book> bookList = new ArrayList<>();
        when(bookMapper.mapToBookDtoList(bookList)).thenReturn(bookDtoList);

        //When & Then
        mockMvc.perform(get("/v1/library/book/getBooks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].bookId", is(1)))
                .andExpect(jsonPath("$[0].title", is("Pan Tadeusz")))
                .andExpect(jsonPath("$[0].author", is("Adam Mickiewicz")))
                .andExpect(jsonPath("$[0].dateOfPublication", is("1834-06-28")));

    }

    @Test
    public void shouldFetchGetBook() throws Exception {
        //Given
        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        when(dbBook.getBook(1L)).thenReturn(Optional.ofNullable(book));
        when(bookMapper.mapToBookDto(any(Book.class))).thenReturn(bookDto);
        //When & Then
        mockMvc.perform(get("/v1/library/book/getBook?bookId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(1)))
                .andExpect(jsonPath("$.title", is("Pan Tadeusz")))
                .andExpect(jsonPath("$.author", is("Adam Mickiewicz")))
                .andExpect(jsonPath("$.dateOfPublication", is("1834-06-28")));
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        //Given
        Book book = new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
        dbBook.deleteById(book.getBookId());
        //When & Then
        mockMvc.perform(delete("/v1/library/book/deleteBook?bookId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
//    @Test
//    public void shouldUpdateBook() throws Exception {
//        //Given
//        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
//        when(bookMapper.mapToBookDto(dbBook.saveBook(bookMapper.mapToBook(bookDto)))).thenReturn(bookDto);
//        //When & Then
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(bookDto);
//
//        mockMvc.perform(put("/v1/library/book/updateBook")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.bookId",is(1)))
//                .andExpect(jsonPath("$.title",is("Pan Tadeusz")))
//                .andExpect(jsonPath("$.author",is("Adam Mickiewicz")))
//                .andExpect(jsonPath("$.dateOfPublication",is("1834-06-28")));
//    }
//    @Test
//    public void shouldCreateBook() throws Exception {
//        //Given
//        Book book = new Book();
//        BookDto bookDto = new BookDto(1L, "Pan Tadeusz", "Adam Mickiewicz", LocalDate.of(1834, 6, 28));
//        when(dbBook.saveBook(bookMapper.mapToBook(bookDto))).thenReturn(book);
//        System.out.println(book);
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(bookDto);
//        //When & Then
//        mockMvc.perform(post("/v1/library/book/createBook")
//            .contentType(MediaType.APPLICATION_JSON)
//            .characterEncoding("UTF-8")
//            .content(jsonContent))
//            .andExpect(status().isOk());
//    }
}
