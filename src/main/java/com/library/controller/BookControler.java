package com.library.controller;

import com.library.domain.BookDto;
import com.library.mapper.BookMapper;
import com.library.service.DbBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/book")
public class BookControler {

    @Autowired
    private DbBook service;
    @Autowired
    private BookMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "getBooks")
    public List<BookDto> getBooks() {
        return mapper.mapToBookDtoList(service.getAllBooks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBook")
    public BookDto getBook(@RequestParam Long id) throws NotFoundException {
        return mapper.mapToBookDto(service.getBook(id).orElseThrow(NotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBook")
    public void deleteBook(@RequestParam Long id) {
        service.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBook")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        return mapper.mapToBookDto(service.saveBook(mapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBook", consumes = APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody BookDto bookDto) {
        service.saveBook(mapper.mapToBook(bookDto));
    }
}
