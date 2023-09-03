package com.project.MovieMania.service;

import com.project.MovieMania.domain.DTO.ReportDTO;
import com.project.MovieMania.domain.DTO.ReviewDTO;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.Recommend;
import com.project.MovieMania.domain.Report;
import com.project.MovieMania.domain.Review;
import com.project.MovieMania.domain.type.ReportType;
import lombok.extern.java.Log;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface MovieService {

    Movie findById(Long id);

    String reserveRate(Long id);

    Long audiNum(Long id);

    String avgScore(Long id);

    List<Integer> audiPerDay(Long id);

    List<Integer>  reserveRateForAge(Long id);

    List<Integer> reserveRateForGender(Long id);

    List<ReviewDTO> getReview(Long id);

    int reviewWrite(Long movieId, int score, String content, Long userId);

    long findRecommend(Long userId, Long reviewId);

    long deleteRecommend(Long userId, Long reviewId);

    long addRecommend(Long userId, Long reviewId);

    long findReport(Long userId, Long reviewId);

    ReportDTO addReport(Long userId, Long reviewId, ReportType reportType);

    long deleteReview(Long reviewId);

    long countRecommend(Long reviewId);

    List<ReviewDTO> getReviewByScore(Long id);

    List<ReviewDTO> getReviewByRecommend(Long id);

    List<Review> findMyReview(Long id);

    List<Review> findMyReviewList(Model model,Integer page,long id);
}
