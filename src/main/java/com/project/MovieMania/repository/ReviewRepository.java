package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie(Movie movie);

    List<Review> findByMovieOrderByCreatedAtDesc(Movie movie);

    List<Review> findByMovieOrderByScoreDesc(Movie movie);

    List<Review> findByUser_id(Long id);
}
