package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Report;
import com.project.MovieMania.domain.Review;
import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    long countByUserAndReview(User user, Review review);
}
