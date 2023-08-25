package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
