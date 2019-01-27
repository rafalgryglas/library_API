package com.library.service;

import com.library.domain.Reader;
import com.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbReader {
    @Autowired
    private ReaderRepository repository;

    public List<Reader> getReaders() {
        return repository.findAll();
    }

    public Optional<Reader> getReader(final Long id) {
        return repository.findById(id);
    }

    public Reader saveReader(final Reader reader) {
        return repository.save(reader);
    }

    public void deleteReader(final Long id) {
        try {
            repository.delete(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
