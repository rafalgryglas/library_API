package com.library.repository;

import com.library.domain.CopyOfBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CopyRepository extends CrudRepository<CopyOfBook, Long> {
    @Override
    List<CopyOfBook> findAll();

    Optional<CopyOfBook> findByBooksListId(Long booksListId);

    @Override
    CopyOfBook save(CopyOfBook copyOfBook);

    @Override
    void delete(Long booksListId);
}
