package com.komsije.booking.mapper;

import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.model.Report;
import com.komsije.booking.repository.AccountRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class ReportMapper {
    @Autowired
    AccountRepository accountRepository;

    public ReportDto toDto(Report report){
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportDto.setReason(report.getReason());
        reportDto.setReportedUserId(report.getReportedUser().getId());
        reportDto.setAuthorId(report.getAuthor().getId());
        reportDto.setDate(report.getDate());
        return reportDto;
    }
    public Report fromDto(ReportDto reportDto){
        Report report = new Report();
        report.setReason(reportDto.getReason());
        report.setDate(reportDto.getDate());
        report.setAuthor(accountRepository.getReferenceById(reportDto.getAuthorId()));
        report.setReportedUser(accountRepository.getReferenceById(reportDto.getReportedUserId()));
        return report;
    }
    public List<ReportDto> toDto(List<Report> reportList){
        List<ReportDto> reports = new ArrayList<>();
        for (Report r: reportList) {
            reports.add(this.toDto(r));
        }
        return reports;
    }
//    public void update(@MappingTarget Report report, ReportDto reportDto)
}
