package com.project.MovieMania.repository;

import com.project.MovieMania.domain.DTO.MovieDTO;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.type.ShowInfoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowInfoRepository extends JpaRepository<ShowInfo, Long> {

    List<ShowInfo> findByshowDateTimeAfter(LocalDateTime nowPlusTwo);

    List<ShowInfo> findByshowDateTimeAfterAndMovie(LocalDateTime nowPlusTwo, Movie movie);

    List<ShowInfo> findByshowDateTimeBeforeAndMovie(LocalDateTime now, Movie movie);

    List<ShowInfo> findByshowDateTimeAfterAndShowDateTimeBeforeAndMovie(LocalDateTime startDay, LocalDateTime endDay, Movie movie);

    ShowInfo findByShowDateTime(LocalDateTime showDateTime);

    ShowInfo findByMovieIdAndTheaterIdAndShowDateTime(Long movieId,Long theaterId,LocalDateTime showDateTime);

    List<ShowInfo> findByStatus(ShowInfoStatus status);

    List<ShowInfo> findByMovieIdAndShowDateTime(Long movieId,LocalDateTime showDateTime);

    List<ShowInfo> findByMovie(Movie movie);
}
