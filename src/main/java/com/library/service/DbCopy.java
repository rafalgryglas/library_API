package com.library.service;

import com.library.domain.CopyOfBook;
import com.library.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbCopy {
    @Autowired
    private CopyRepository repository;

    public List<CopyOfBook> getAllCopies() {
        return repository.findAll();
    }

    public Optional<CopyOfBook> getCopy(final Long booksListId) {
        return repository.findByBooksListId(booksListId);
    }

    public CopyOfBook saveCopy(final CopyOfBook copyOfBook) {
        return repository.save(copyOfBook);
    }

    public void deleteCopyOfBook(final Long booksListId) {
        try {
            repository.delete(booksListId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
