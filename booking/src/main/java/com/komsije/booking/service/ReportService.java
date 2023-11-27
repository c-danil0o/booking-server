package com.komsije.booking.service;

import com.komsije.booking.model.Report;
import com.komsije.booking.model.Review;
import com.komsije.booking.repository.ReportRepository;
import com.komsije.booking.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report findOne(Long id) {
        return reportRepository.findById(id).orElseGet(null);
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public void remove(Long id) {
        reportRepository.deleteById(id);
    }
}
