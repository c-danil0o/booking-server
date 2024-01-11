package com.komsije.booking.controller;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.dto.ReportViewDto;
import com.komsije.booking.model.Report;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/reports")
public class ReportController {
    private final AccountService accountService;
    private final ReportService reportService;

    @Autowired
    public ReportController(AccountService accountService, ReportService reportService) {
        this.accountService = accountService;
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<ReportViewDto>> getAllReports() {
        List<ReportViewDto> reports = reportService.getAll();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReportDto> saveReport(@RequestBody ReportDto reportDto){
        ReportDto reportDTO = null;
        reportDTO = reportService.save(reportDto);
        return new ResponseEntity<>(reportDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
