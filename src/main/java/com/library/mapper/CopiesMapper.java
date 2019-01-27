package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.CopiesOfBook;
import com.library.domain.CopiesOfBookDto;
import com.library.service.DbBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopiesMapper {
    @Autowired
    private DbBook dbBook;

    public CopiesOfBook mapToCopies(final CopiesOfBookDto copiesOfBookDto) {
        Book book = dbBook.getBook(copiesOfBookDto.getBookId()).orElse(null);
        return new CopiesOfBook(
                copiesOfBookDto.getBooksListId(),
                book,
                copiesOfBookDto.getBookStatus());
    }

    public CopiesOfBookDto mapToCopiesDto(final CopiesOfBook copiesOfBook) {
        return new CopiesOfBookDto(
                copiesOfBook.getBooksListId(),
                copiesOfBook.getBook().getBookId(),
                copiesOfBook.getBookStatus());
    }

    public List<CopiesOfBookDto> mapToCopiesDtoList(final List<CopiesOfBook> copiesList) {
        return copiesList.stream()
                .map(c -> new CopiesOfBookDto(c.getBooksListId(), c.getBook().getBookId(), c.getBookStatus()))
                .collect(Collectors.toList());
    }
}
