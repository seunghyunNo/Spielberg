package com.project.MovieMania.service;

import com.project.MovieMania.domain.ShowInfo;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

public interface ShowInfoService {
    ShowInfo writeShowInfo(Long movieId, String cinemaName, LocalDateTime showDateTime, Model model);

    ShowInfo findById(Long showInfoId,Model model);
}
