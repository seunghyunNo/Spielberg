package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempRepository extends JpaRepository<Movie, Long> {
}
