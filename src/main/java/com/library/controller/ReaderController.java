package com.library.controller;

import com.library.domain.ReaderDto;
import com.library.mapper.ReaderMapper;
import com.library.service.DbReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/library/reader")
public class ReaderController {

    @Autowired
    private DbReader service;
    @Autowired
    private ReaderMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "getReaders")
    public List<ReaderDto> getReaders() {
        return mapper.mapToReaderDtoList(service.getReaders());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getReader")
    public ReaderDto getReader(@RequestParam Long readerId) throws NotFoundException {
        return mapper.mapToReaderDto(service.getReader(readerId).orElseThrow(NotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteReader")
    public void deleteReader(@RequestParam Long readerId) {
        service.deleteReader(readerId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateReader")
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        return mapper.mapToReaderDto(service.saveReader(mapper.mapToReader(readerDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createReader", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        service.saveReader(mapper.mapToReader(readerDto));
    }
}
