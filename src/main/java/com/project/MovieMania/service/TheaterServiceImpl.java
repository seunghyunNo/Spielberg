package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;
import com.project.MovieMania.domain.type.ShowInfoStatus;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.MovieRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService{


    private CinemaRepository cinemaRepository;

    private TheaterRepository theaterRepository;

    private ShowinfoRepoisotry showinfoRepoisotry;

    private MovieRepository movieRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Autowired
    public void setCinemaRepository(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Autowired
    public void setTheaterRepository(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Autowired
    public void setShowinfoRepoisotry(ShowinfoRepoisotry showinfoRepoisotry) {
        this.showinfoRepoisotry = showinfoRepoisotry;
    }


    @Override
    public List<Cinema> cinemaList() {
        cinemaRepository.findAll().forEach(System.out::println);
        return cinemaRepository.findAll();
    }

    @Override
    public List<LocalDate> dateList() {
        List<ShowInfo> showInfos = showinfoRepoisotry.findAll();
        List<LocalDate> dates =new ArrayList<>();

        for(int i = 0; i < showInfos.size(); i++)
        {
            dates.add(showInfos.get(i).getShowDateTime().toLocalDate());
        }

        return dates;
    }

    @Override
    public List<LocalTime> timeList() {
        List<ShowInfo> showInfos = showinfoRepoisotry.findAll();
        List<LocalTime> times =new ArrayList<>();

        for(int i = 0; i < showInfos.size(); i++)
        {
            times.add(showInfos.get(i).getShowDateTime().toLocalTime());
        }

        return times;
    }


}
