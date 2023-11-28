package com.komsije.booking.mapper;

import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.model.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class})
public interface ReportMapper {
    ReportDto toDto(Report report);
    Report fromDto(ReportDto reportDto);
}
