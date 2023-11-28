package com.komsije.booking.service;

import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.mapper.ReportMapper;
import com.komsije.booking.model.Report;
import com.komsije.booking.repository.ReportRepository;
import com.komsije.booking.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private ReportMapper mapper;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportDto findById(Long id) {
        return mapper.toDto(reportRepository.findById(id).orElseGet(null));
    }

    public List<ReportDto> findAll() {
        return mapper.toDto(reportRepository.findAll());
    }

    public ReportDto save(ReportDto reportDto) {
        reportRepository.save(mapper.fromDto(reportDto));
        return reportDto;
    }

    @Override
    public ReportDto update(ReportDto reportDto) {
        Report report = reportRepository.findById(reportDto.getId()).orElseGet(null);
        if (report == null){
            return null;
        }
        Report updatedReport = mapper.fromDto(reportDto);
        reportRepository.save(updatedReport);
        return reportDto;
    }

    public void delete(Long id) {
        reportRepository.deleteById(id);
    }
}
