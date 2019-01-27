package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookMapper {
    public Book mapToBook(final BookDto bookDto) {
        return new Book(
                bookDto.getBookId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getDateOfPublication());
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDateOfPublication());
    }

    public List<BookDto> mapToBookDtoList(final List<Book> booksList) {
        return booksList.stream()
                .map(b -> new BookDto(b.getBookId(), b.getTitle(), b.getAuthor(), b.getDateOfPublication()))
                .collect(Collectors.toList());
    }
}
