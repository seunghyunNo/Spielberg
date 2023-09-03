package com.project.MovieMania.service;

import com.project.MovieMania.domain.DTO.AdminReportDTO;
import com.project.MovieMania.domain.DTO.ReviewDTO;
import com.project.MovieMania.domain.DTO.UserDTO;
import com.project.MovieMania.domain.Report;
import com.project.MovieMania.domain.Review;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.ReportType;
import com.project.MovieMania.repository.ReportRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminReportServiceImpl implements AdminReportService {
	private ReportRepository reportRepository;
	private UserRepository userRepository;
	
	@Autowired
	public void setReportRepository(ReportRepository reportRepository){
		this.reportRepository = reportRepository;
	}
	@Autowired
	public void setUserRepository(UserRepository userRepository){ this.userRepository = userRepository; }
	
	@Override
	public List<AdminReportDTO> list(){
		List<AdminReportDTO> adminReports = new ArrayList<>();
		List<Report> reports = reportRepository.findAll();
		reports.forEach(element -> {
			AdminReportDTO adminReport = new AdminReportDTO();
			adminReport.setId(element.getId());
			adminReport.setReportType(element.getType());
			
			UserDTO reporterDTO = new UserDTO();
			User reporter = element.getUser();
			reporterDTO.setId(reporter.getId());
			reporterDTO.setUsername(reporter.getUsername());
			adminReport.setReporter(reporterDTO);
			
			Review review = element.getReview();
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setId(review.getId());
			reviewDTO.setContent(review.getContent());
			reviewDTO.setCreatedAt(review.getCreatedAt());
			adminReport.setReview(reviewDTO);
			
			User writer = review.getUser();
			UserDTO writerDTO = new UserDTO();
			writerDTO.setId(writer.getId());
			writerDTO.setUsername(writer.getUsername());
			adminReport.setWriter(writerDTO);
			
			adminReports.add(adminReport);
		});
		return adminReports;
	}
	
	@Override
	public List<AdminReportDTO> list(ReportType reportType){
		List<AdminReportDTO> adminReports = new ArrayList<>();
		List<Report> reports = reportRepository.findByType(reportType);
		reports.forEach(element -> {
			AdminReportDTO adminReport = new AdminReportDTO();
			adminReport.setId(element.getId());
			adminReport.setReportType(element.getType());
			
			UserDTO reporterDTO = new UserDTO();
			User reporter = element.getUser();
			reporterDTO.setId(reporter.getId());
			reporterDTO.setUsername(reporter.getUsername());
			adminReport.setReporter(reporterDTO);
			
			Review review = element.getReview();
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setId(review.getId());
			reviewDTO.setContent(review.getContent());
			reviewDTO.setCreatedAt(review.getCreatedAt());
			adminReport.setReview(reviewDTO);
			
			User writer = review.getUser();
			UserDTO writerDTO = new UserDTO();
			writerDTO.setId(writer.getId());
			writerDTO.setUsername(writer.getUsername());
			adminReport.setWriter(writerDTO);
			
			adminReports.add(adminReport);
		});
		return adminReports;
	}
	
	@Override
	public int delete(Long id) {
		try{
			Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException());
			reportRepository.delete(report);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
	
	
}
