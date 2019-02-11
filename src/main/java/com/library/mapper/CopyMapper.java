package com.library.mapper;

import com.library.domain.Book;
import com.library.domain.CopyOfBook;
import com.library.domain.CopyOfBookDto;
import com.library.service.DbBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopyMapper {
    @Autowired
    private DbBook dbBook;

    public CopyOfBook mapToCopy(final CopyOfBookDto copyOfBookDto) {
        Book book = dbBook.getBook(copyOfBookDto.getBookId()).orElse(null);
        return new CopyOfBook(
                copyOfBookDto.getBooksListId(),
                book,
                copyOfBookDto.getBookStatus());
    }

    public CopyOfBookDto mapToCopyDto(final CopyOfBook copyOfBook) {
        return new CopyOfBookDto(
                copyOfBook.getBooksListId(),
                copyOfBook.getBook().getBookId(),
                copyOfBook.getBookStatus());
    }

    public List<CopyOfBookDto> mapToCopyDtoList(final List<CopyOfBook> copiesList) {
        return copiesList.stream()
                .map(c -> new CopyOfBookDto(c.getBooksListId(), c.getBook().getBookId(), c.getBookStatus()))
                .collect(Collectors.toList());
    }
}
