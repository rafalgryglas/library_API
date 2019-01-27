package com.library.repository;

import com.library.domain.Borrows;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowsRepository extends CrudRepository<Borrows, Long> {
    @Override
    List<Borrows> findAll();

    Optional<Borrows> findByBorrowerId(Long borrowerId);

    @Override
    Borrows save(Borrows borrows);

    @Override
    void delete(Long borrowerId);
}
