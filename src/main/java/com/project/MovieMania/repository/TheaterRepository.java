package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Theater findByCinemaId(Long cinemaId);
}
