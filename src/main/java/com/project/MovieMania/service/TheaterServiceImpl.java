package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.MovieRepository;
import com.project.MovieMania.repository.ShowInfoRepository;
import com.project.MovieMania.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TheaterServiceImpl implements TheaterService{


    private CinemaRepository cinemaRepository;

    private TheaterRepository theaterRepository;

    private ShowInfoRepository showinfoRepository;

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
    public void setShowinfoRepoisotry(ShowInfoRepository showinfoRepository) {
        this.showinfoRepository = showinfoRepository;
    }


    @Override
    public List<Cinema> cinemaList() {
        cinemaRepository.findAll().forEach(System.out::println);
        return cinemaRepository.findAll();
    }

    @Override
    public Set<LocalDate> dateList() {
        List<ShowInfo> showInfos = showinfoRepository.findAll();
        Set<LocalDate> dates =new HashSet<>();

        for(int i = 0; i < showInfos.size(); i++)
        {
            dates.add(showInfos.get(i).getShowDateTime().toLocalDate());
        }

        return dates;
    }

    @Override
    public Set<LocalTime> timeList() {
        List<ShowInfo> showInfos = showinfoRepository.findAll();
        Set<LocalTime> times =new HashSet<>();

        for(int i = 0; i < showInfos.size(); i++)
        {
            times.add(showInfos.get(i).getShowDateTime().toLocalTime());
        }

        return times;
    }


}
