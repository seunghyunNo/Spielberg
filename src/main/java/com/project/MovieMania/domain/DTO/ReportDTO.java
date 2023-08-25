package com.project.MovieMania.domain.DTO;

import com.project.MovieMania.domain.type.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private Long id;
    private ReportType reportType;
}
