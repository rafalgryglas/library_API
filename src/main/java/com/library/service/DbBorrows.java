package com.library.service;

import com.library.domain.Borrows;
import com.library.repository.BorrowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbBorrows {
    @Autowired
    private BorrowsRepository repository;

    public List<Borrows> getAll() {
        return repository.findAll();
    }

    public Optional<Borrows> getBorrowerById(final Long borrowerId) {
        return repository.findByBorrowerId(borrowerId);
    }

    public void deleteByBorrowerId(final Long borrowerId) {
        try {
            repository.delete(borrowerId);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Borrows save(Borrows borrows) {
        return repository.save(borrows);
    }
}
