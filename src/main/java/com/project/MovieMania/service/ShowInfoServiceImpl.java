package com.project.MovieMania.service;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.type.ShowInfoStatus;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.MovieRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

@Service
public class ShowInfoServiceImpl implements ShowInfoService{

    private MovieRepository movieRepository;

    private ShowinfoRepoisotry showinfoRepoisotry;

    private CinemaRepository cinemaRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Autowired
    public void setShowinfoRepoisotry(ShowinfoRepoisotry showinfoRepoisotry) {
        this.showinfoRepoisotry = showinfoRepoisotry;
    }

    @Autowired
    public void setCinemaRepository(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public ShowInfo writeShowInfo(Long movieId, String cinemaName, LocalDateTime showDateTime, Model model) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        Cinema cinema = cinemaRepository.findByName(cinemaName);
        ShowInfo showInfoTime = showinfoRepoisotry.findByShowDateTime(showDateTime);

        ShowInfo showInfo = ShowInfo.builder()
                .movie(movie)
                .theater(showInfoTime.getTheater())
                .showDateTime(showDateTime)
                .status(ShowInfoStatus.NOW)
                .build();

        showinfoRepoisotry.saveAndFlush(showInfo);

        model.addAttribute("showInfoId",showInfo.getId());

        System.out.println(showInfo);
        return showInfo;
    }

    @Override
    public ShowInfo findById(Long showInfoId) {
        ShowInfo showInfo = showinfoRepoisotry.findById(showInfoId).orElse(null);
        return showInfo;
    }
}
