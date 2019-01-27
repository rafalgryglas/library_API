package com.library.controller;

import com.library.domain.CopiesOfBookDto;
import com.library.mapper.CopiesMapper;
import com.library.service.DbCopies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/copiesList")
public class CopiesControler {

    @Autowired
    private DbCopies service;
    @Autowired
    private CopiesMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "getListCopies")
    public List<CopiesOfBookDto> getCopiesList() {
        return mapper.mapToCopiesDtoList(service.getAllCopies());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCopyOfBook")
    public CopiesOfBookDto getCopyOfBook(@RequestParam Long id) throws NotFoundException {
        return mapper.mapToCopiesDto(service.getCopy(id).orElseThrow(NotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteCopy")
    public void deleteById(@RequestParam Long id) {
        service.deleteCopyOfBook(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateCopyOfBook")
    public CopiesOfBookDto updateCopy(@RequestBody CopiesOfBookDto copiesOfBookDto) {
        return mapper.mapToCopiesDto(service.saveCopy(mapper.mapToCopies(copiesOfBookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCopyOfBook", consumes = APPLICATION_JSON_VALUE)
    public void createCopyOfBook(@RequestBody CopiesOfBookDto copiesOfBookDto) {
        service.saveCopy(mapper.mapToCopies(copiesOfBookDto));
    }
}
