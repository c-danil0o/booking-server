package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.ReportDto;
import com.komsije.booking.dto.ReportViewDto;
import com.komsije.booking.model.Report;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface ReportService extends CrudService<ReportDto, Long> {
    public List<ReportViewDto> getAll();
}
