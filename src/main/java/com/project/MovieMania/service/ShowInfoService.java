package com.project.MovieMania.service;

import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowInfoService {
    ShowInfo findShowInfo(Long movieId, String cinemaName, LocalDateTime showDateTime,Model model);

    ShowInfo findById(Long showInfoId,Model model);

    List<ShowInfo> findByMovie(Long movieId);

    List<ShowInfo> findByMovieAndTheater(Long movieId,Theater theater);
}
