package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface TheaterService {

    List<Cinema> cinemaList();

    Set<Cinema> cinemaSet(Long movieId);

    Set<LocalDate> dateList();

    Set<LocalTime> timeList();

    Theater findById(Long theaterId);


}
