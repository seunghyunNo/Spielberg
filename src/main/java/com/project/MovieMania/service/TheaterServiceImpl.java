package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TheaterServiceImpl implements TheaterService{


    private CinemaRepository cinemaRepository;

    private TheaterRepository theaterRepository;

    private ShowinfoRepoisotry showinfoRepoisotry;

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
    public Long selectCinema(String cinemaName) {
        Cinema cinema = cinemaRepository.findByName(cinemaName);
        return cinema.getId();
    }

    @Override
    public ShowInfo selectShowInfo(Long showInfoId) {
        ShowInfo showInfo = showinfoRepoisotry.findById(showInfoId).orElse(null);
        return showInfo;
    }
}
