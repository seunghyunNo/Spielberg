package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface TheaterService {

    List<Cinema> cinemaList();

    List<LocalDate> dateList();

    List<LocalTime> timeList();


}
