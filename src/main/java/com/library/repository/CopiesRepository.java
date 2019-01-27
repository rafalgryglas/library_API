package com.library.repository;

import com.library.domain.CopiesOfBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CopiesRepository extends CrudRepository<CopiesOfBook, Long> {
    @Override
    List<CopiesOfBook> findAll();

    Optional<CopiesOfBook> findByBooksListId(Long booksListId);

    @Override
    CopiesOfBook save(CopiesOfBook copiesOfBook);

    @Override
    void delete(Long booksListId);
}
