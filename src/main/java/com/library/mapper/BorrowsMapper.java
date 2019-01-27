package com.library.mapper;

import com.library.domain.*;
import com.library.service.DbCopies;
import com.library.service.DbReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowsMapper {
    @Autowired
    private DbReader dbReader;
    @Autowired
    private DbCopies dbCopies;

    public Borrows mapToBorrows(final BorrowsDto borrowsDto) {
        Reader reader = dbReader.getReader(borrowsDto.getReaderId()).orElse(null);//orElseThrow
        CopiesOfBook copies = dbCopies.getCopy(borrowsDto.getBooksListId()).orElse(null);
        return new Borrows(
                borrowsDto.getBorrowerId(),
                reader,
                copies,
                borrowsDto.getRentalBook(),
                borrowsDto.getReturnBook());
    }

    public BorrowsDto mapToBorrowsDto(final Borrows borrows) {
        return new BorrowsDto(
                borrows.getBorrowerId(),
                borrows.getReader().getId(),
                borrows.getCopiesOfBook().getBooksListId(),
                borrows.getRentalBook(),
                borrows.getReturnBook());
    }

    public List<BorrowsDto> mapToBorrowsDtoList(final List<Borrows> borrowsList) {
        return borrowsList.stream()
                .map(b -> new BorrowsDto(b.getBorrowerId(), b.getReader().getId(),
                        b.getCopiesOfBook().getBooksListId(), b.getRentalBook(), b.getReturnBook()))
                .collect(Collectors.toList());
    }
}
