package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getName(),
                readerDto.getLastname(),
                readerDto.getDateCreateAccount());
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getName(),
                reader.getLastname(),
                reader.getDateCreateAccount());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readersList) {
        return readersList.stream()
                .map(r -> new ReaderDto(r.getId(), r.getName(), r.getLastname(), r.getDateCreateAccount()))
                .collect(Collectors.toList());
    }
}
