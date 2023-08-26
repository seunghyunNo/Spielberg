package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDateTime;

public interface ShowinfoRepoisotry extends JpaRepository<ShowInfo, Long> {

    List<ShowInfo> findByshowDateTimeAfter(LocalDateTime nowPlusTwo);

    List<ShowInfo> findByshowDateTimeAfterAndMovie(LocalDateTime nowPlusTwo, Movie movie);

    List<ShowInfo> findByshowDateTimeBeforeAndMovie(LocalDateTime now, Movie movie);

    List<ShowInfo> findByshowDateTimeAfterAndShowDateTimeBeforeAndMovie(LocalDateTime startDay, LocalDateTime endDay, Movie movie);

    ShowInfo findByShowDateTime(LocalDateTime showDateTime);

    List<ShowInfo> findByMovieIdAndShowDateTime(Long movieId,LocalDateTime showDateTime);
}
