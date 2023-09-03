package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.Review;
import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie(Movie movie);

    List<Review> findByMovieOrderByCreatedAtDesc(Movie movie);

    List<Review> findByMovieOrderByScoreDesc(Movie movie);

    @Query("SELECT r " +
            "FROM Review r LEFT JOIN r.recommends rc " +
            "WHERE r.movie.id = :movieId " +
            "GROUP BY r.id " +
            "ORDER BY COUNT(rc) DESC")
    List<Review> findReviewOrderByRecommendCountDesc(@Param("movieId") Long movieId);

    List<Review> findByUser(User user);
}
