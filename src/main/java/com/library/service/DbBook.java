package com.library.service;

import com.library.domain.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbBook {
    @Autowired
    private BookRepository repository;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> getBook(final Long id) {
        return repository.findByBookId(id);
    }

    public Book saveBook(final Book book) {
        return repository.save(book);
    }

    public void deleteById(final Long id) {
        try {
            repository.delete(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
