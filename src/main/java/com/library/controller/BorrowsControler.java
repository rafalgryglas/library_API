package com.library.controller;

import com.library.domain.BorrowsDto;
import com.library.domain.CopiesOfBookDto;
import com.library.mapper.BorrowsMapper;
import com.library.service.DbBorrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/borrows")
public class BorrowsControler {

    @Autowired
    private DbBorrows service;
    @Autowired
    private BorrowsMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "getAllBorrows")
    public List<BorrowsDto> getAllBorrows() {
        return mapper.mapToBorrowsDtoList(service.getAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBorrower")
    public BorrowsDto getBorrower(@RequestParam Long id) throws NotFoundException {
        return mapper.mapToBorrowsDto(service.getBorrowerById(id).orElseThrow(NotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBorrower")
    public void deleteBorrower(@RequestParam Long id) {
        service.deleteByBorrowerId(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBorrower")
    public BorrowsDto updateBorrower(@RequestBody BorrowsDto borrowsDto) {
        return mapper.mapToBorrowsDto(service.save(mapper.mapToBorrows(borrowsDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBorrower", consumes = APPLICATION_JSON_VALUE)
    public void createBorrower(@RequestBody BorrowsDto borrowsDto) {
        service.save(mapper.mapToBorrows(borrowsDto));
    }
}
