package com.library.repository;

import com.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
    @Override
    List<Reader> findAll();

    Optional<Reader> findById(Long id);

    @Override
    Reader save(Reader reader);

    @Override
    void delete(Long id);
}
