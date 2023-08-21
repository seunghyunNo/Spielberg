package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findByName(String cinemaName);
}
