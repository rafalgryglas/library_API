package com.library.controller;

import com.library.domain.BorrowerDto;
import com.library.domain.BorrowerResponseDto;
import com.library.service.DbBorrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/borrower")
public class BorrowerController {

    @Autowired
    private DbBorrower service;

    @PostMapping(value = "borrowBook")
    public BorrowerResponseDto borrowBook(@RequestBody BorrowerDto borrowerDto) {
        boolean result = service.borrowBook(borrowerDto);
        return new BorrowerResponseDto(result);
    }

    @PostMapping(value = "returnBook")
    public BorrowerResponseDto returnBook(@RequestParam Long borrowerId) {
        boolean result = service.returnBook(borrowerId);
        return new BorrowerResponseDto(result);
    }
}
