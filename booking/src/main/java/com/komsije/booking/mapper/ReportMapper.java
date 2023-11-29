package com.komsije.booking.mapper;

import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface ReportMapper {
    ReportDto toDto(Report report);
    Report fromDto(ReportDto reportDto);
    List<ReportDto> toDto(List<Report> reportList);
    void update(@MappingTarget Report report, ReportDto reportDto);
}
