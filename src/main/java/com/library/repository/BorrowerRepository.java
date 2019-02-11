package com.library.repository;

import com.library.domain.Borrower;
import com.library.domain.CopyOfBook;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BorrowerRepository extends CrudRepository<Borrower, Long> {

    Optional<Borrower> findByBorrowerId(Long borrowerId);

    @Override
    Borrower save(Borrower borrower);

    Borrower findByCopyOfBook(CopyOfBook copy);

}
