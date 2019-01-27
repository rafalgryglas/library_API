package com.library.service;

import com.library.domain.CopiesOfBook;
import com.library.repository.CopiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbCopies {
    @Autowired
    private CopiesRepository repository;

    public List<CopiesOfBook> getAllCopies() {
        return repository.findAll();
    }

    public Optional<CopiesOfBook> getCopy(final Long booksListId) {
        return repository.findByBooksListId(booksListId);
    }

    public CopiesOfBook saveCopy(final CopiesOfBook copiesOfBook) {
        return repository.save(copiesOfBook);
    }

    public void deleteCopyOfBook(final Long booksListId) {
        try {
            repository.delete(booksListId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
