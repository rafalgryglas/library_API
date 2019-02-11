package com.library.service;

import com.library.domain.BookStatus;
import com.library.domain.Borrower;
import com.library.domain.BorrowerDto;
import com.library.domain.CopyOfBook;
import com.library.mapper.BorrowerMapper;
import com.library.repository.BorrowerRepository;
import com.library.repository.CopyRepository;
import com.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DbBorrower {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BorrowerMapper borrowerMapper;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private DbCopy dbCopy;

    public boolean borrowBook(BorrowerDto borrowerDto) {
        Borrower borrower = borrowerMapper.mapToBorrows(borrowerDto);
        if (borrower.getCopyOfBook().getBookStatus() == BookStatus.FORRENT) {
            borrower.getCopyOfBook().setBookStatus(BookStatus.BORROWED);
            CopyOfBook copy = dbCopy.getCopy(borrower.getCopyOfBook().getBooksListId()).orElse(null);
            if (copy == null) {
                return false;
            }
            copy.setBookStatus(BookStatus.BORROWED);
            borrower.setReader(readerRepository.findById(borrowerDto.getReaderId()).orElse(null));
            borrower.setCopyOfBook(copyRepository.findByBooksListId(borrowerDto.getBooksListId()).orElse(null));
            borrower.setRentalBookDate(LocalDate.now());
            borrowerRepository.save(borrower);
            dbCopy.saveCopy(copy);
            return true;
        }
        return false;
    }

    public boolean returnBook(Long bookCopyId) {
        CopyOfBook copy = dbCopy.getCopy(bookCopyId).orElse(null);
        if (copy == null) {
            return false;
        }
        Borrower borrower = borrowerRepository.findByCopyOfBook(copy);
        if (borrower == null || borrower.getReturnBookDate() != null || borrower.getBorrowerId() == null) {
            return false;
        }
        borrower.setReturnBookDate(LocalDate.now());
        copy.setBookStatus(BookStatus.FORRENT);
        borrowerRepository.save(borrower);
        dbCopy.saveCopy(copy);
        return true;
    }
}
