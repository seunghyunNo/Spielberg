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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowInfoServiceImpl implements ShowInfoService{

    private MovieRepository movieRepository;

    private ShowinfoRepoisotry showinfoRepoisotry;

    private CinemaRepository cinemaRepository;

    private TheaterRepository theaterRepository;

    @Autowired
    public void setTheaterRepository(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

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
    public ShowInfo findShowInfo(Long movieId, String cinemaName, LocalDateTime showDateTime, Model model) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        Cinema cinema = cinemaRepository.findByName(cinemaName);
        List<ShowInfo> showInfoList =showinfoRepoisotry.findByMovieIdAndShowDateTime(movie.getId(),showDateTime);
        ShowInfo showInfo = new ShowInfo();
        int theaterNum = 0;
        for(int i = 0 ; i < showInfoList.size() ; i++)
        {
            String name = showInfoList.get(i).getTheater().getCinema().getName();

            if(name.equals(cinemaName))
            {
               theaterNum = showInfoList.get(i).getTheater().getTheaterNum();
                System.out.println("상영관번호"+theaterNum);
               showInfo = showInfoList.get(i);
            }
        }
            model.addAttribute("showInfoId",showInfo.getId());
            System.out.println(showInfo);
            return showInfo;
    }

    @Override
    public ShowInfo findById(Long showInfoId,Model model) {
        ShowInfo showInfo = showinfoRepoisotry.findById(showInfoId).orElse(null);
        model.addAttribute("showInfoId",showInfo.getId());
        System.out.println(showInfo);
        return showInfo;
    }
}
