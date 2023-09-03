package com.project.MovieMania.domain.DTO;

import com.project.MovieMania.domain.type.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminReportDTO {
	private Long id;
	private ReportType reportType;
	private UserDTO reporter;
	private ReviewDTO review;
	private UserDTO writer;
}
