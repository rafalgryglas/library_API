package com.library.controller;

import com.library.domain.CopyOfBookDto;
import com.library.mapper.CopyMapper;
import com.library.service.DbCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/copiesList")
public class CopyController {

    @Autowired
    private DbCopy service;
    @Autowired
    private CopyMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "getListCopies")
    public List<CopyOfBookDto> getCopiesList() {
        return mapper.mapToCopyDtoList(service.getAllCopies());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getCopyOfBook")
    public CopyOfBookDto getCopyOfBook(@RequestParam Long copyId) throws NotFoundException {
        return mapper.mapToCopyDto(service.getCopy(copyId).orElseThrow(NotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteCopy")
    public void deleteById(@RequestParam Long copyId) {
        service.deleteCopyOfBook(copyId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateCopyOfBook")
    public CopyOfBookDto updateCopy(@RequestBody CopyOfBookDto copyOfBookDto) {
        return mapper.mapToCopyDto(service.saveCopy(mapper.mapToCopy(copyOfBookDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createCopyOfBook", consumes = APPLICATION_JSON_VALUE)
    public void createCopyOfBook(@RequestBody CopyOfBookDto copyOfBookDto) {
        service.saveCopy(mapper.mapToCopy(copyOfBookDto));
    }
}
