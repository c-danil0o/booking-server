package com.komsije.booking.service;

import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.dto.ReportViewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.ReportMapper;
import com.komsije.booking.model.Report;
import com.komsije.booking.repository.ReportRepository;
import com.komsije.booking.service.interfaces.ReportService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper mapper;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, ReportMapper mapper) {
        this.reportRepository = reportRepository;
        this.mapper = mapper;
    }

    public ReportDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(reportRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!")));

    }

    public List<ReportDto> findAll() {
        return mapper.toDto(reportRepository.findAll());
    }

    public ReportDto save(ReportDto reportDto) {
        Report report = mapper.fromDto(reportDto);
        reportRepository.save(report);
        return reportDto;
    }

    @Override
    public ReportDto update(ReportDto reportDto) throws ElementNotFoundException {
//        Report report = reportRepository.findById(reportDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
//        mapper.update(report, reportDto);
//        reportRepository.save(report);
        return reportDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (reportRepository.existsById(id)){
            reportRepository.deleteById(id);
        }else{
            throw new ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }

    @Override
    public List<ReportViewDto> getAll() {
        return mapper.toViewDto(reportRepository.findAll());
    }
}
