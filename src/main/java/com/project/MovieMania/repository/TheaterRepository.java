package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {
}
