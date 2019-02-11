package com.library.mapper;

import com.library.domain.Borrower;
import com.library.domain.BorrowerDto;
import com.library.domain.CopyOfBook;
import com.library.domain.Reader;
import com.library.service.DbCopy;
import com.library.service.DbReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowerMapper {
    @Autowired
    private DbReader dbReader;
    @Autowired
    private DbCopy dbCopy;

    public Borrower mapToBorrows(final BorrowerDto borrowerDto) {
        Reader reader = dbReader.getReader(borrowerDto.getReaderId()).orElse(null);
        CopyOfBook copies = dbCopy.getCopy(borrowerDto.getBooksListId()).orElse(null);
        return new Borrower(
                borrowerDto.getBorrowerId(),
                reader,
                copies,
                borrowerDto.getRentalBookDate(),
                borrowerDto.getReturnBookDate());
    }

    public BorrowerDto mapToBorrowsDto(final Borrower borrower) {
        return new BorrowerDto(
                borrower.getBorrowerId(),
                borrower.getReader().getId(),
                borrower.getCopyOfBook().getBooksListId(),
                borrower.getRentalBookDate(),
                borrower.getReturnBookDate());
    }

    public List<BorrowerDto> mapToBorrowsDtoList(final List<Borrower> borrowerList) {
        return borrowerList.stream()
                .map(b -> new BorrowerDto(b.getBorrowerId(), b.getReader().getId(),
                        b.getCopyOfBook().getBooksListId(), b.getRentalBookDate(), b.getReturnBookDate()))
                .collect(Collectors.toList());
    }
}
