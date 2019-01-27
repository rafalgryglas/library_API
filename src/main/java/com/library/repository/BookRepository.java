package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Override
    List<Book> findAll();

    Optional<Book> findByBookId(Long id);

    @Override
    Book save(Book book);

    @Override
    void delete(Long id);
}
