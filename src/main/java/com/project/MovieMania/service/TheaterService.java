package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;

import java.time.LocalDateTime;

public interface TheaterService {

    Long selectCinema(String cinemaName);


    ShowInfo selectShowInfo(Long showInfoId);
}
