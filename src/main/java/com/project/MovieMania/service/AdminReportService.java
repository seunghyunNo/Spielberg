package com.project.MovieMania.service;

import com.project.MovieMania.domain.DTO.AdminReportDTO;
import com.project.MovieMania.domain.type.ReportType;

import java.util.List;

public interface AdminReportService {
	List<AdminReportDTO> list(ReportType reportType);
	
	List<AdminReportDTO> list();
	
	int delete(Long id);
}
