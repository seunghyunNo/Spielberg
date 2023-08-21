package com.project.MovieMania.repository;

import com.project.MovieMania.domain.ShowInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ShowinfoRepoisotry extends JpaRepository<ShowInfo, Long> {
    ShowInfo findByShowDateTime(LocalDateTime showDateTime);
}
