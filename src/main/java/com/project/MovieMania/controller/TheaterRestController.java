package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Cinema;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Theater;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.service.ShowInfoService;
import com.project.MovieMania.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ticketSelect")
public class TheaterRestController {

    private TheaterService theaterService;

    private ShowInfoService showInfoService;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    public void setTheaterService(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @Autowired
    public void setShowInfoService(ShowInfoService showInfoService) {
        this.showInfoService = showInfoService;
    }

    @PostMapping("/selectDate")
    public List<LocalDate> selectDate(@RequestParam String cinemaName,@RequestParam String movieId)
    {
        Long id = Long.parseLong(movieId);
        Cinema cinema = cinemaRepository.findByName(cinemaName);
        List<Theater> theater = theaterService.findByCinemaId(cinema.getId());
        Theater theaterInfo = new Theater();
        List<ShowInfo> showInfoList = showInfoService.findByMovie(id);

        for(int i = 0; i < showInfoList.size(); i++)
        {
            if(showInfoList.get(i).getTheater().equals(theater.get(i)))
            {
                theaterInfo = showInfoList.get(i).getTheater();
                break;
            }
        }
        showInfoList.clear();
        showInfoList = showInfoService.findByMovieAndTheater(id,theaterInfo);
        List<LocalDate> dateList = new ArrayList<>();

        for (int i = 0; i < showInfoList.size(); i++)
        {
            dateList.add(showInfoList.get(i).getShowDateTime().toLocalDate());
        }

        return dateList;
    }

    @PostMapping("/selectTime")
    public List<LocalTime> selectTime(@RequestParam String cinemaName,@RequestParam String movieId,@RequestParam String day)
    {
        System.out.println(day);
        Long id = Long.parseLong(movieId);
        Cinema cinema = cinemaRepository.findByName(cinemaName);
        List<Theater> theater = theaterService.findByCinemaId(cinema.getId());
        Theater theaterInfo = new Theater();
        List<ShowInfo> showInfoList = showInfoService.findByMovie(id);

        for(int i = 0; i < showInfoList.size(); i++)
        {
            if(showInfoList.get(i).getTheater().equals(theater.get(i)))
            {
                theaterInfo = showInfoList.get(i).getTheater();
                break;
            }
        }
        showInfoList.clear();
        showInfoList = showInfoService.findByMovieAndTheater(id,theaterInfo);
        List<LocalDate> dateList = new ArrayList<>();
        List<LocalTime> timeList = new ArrayList<>();

        for (int i = 0; i < showInfoList.size(); i++)
        {
            dateList.add(showInfoList.get(i).getShowDateTime().toLocalDate());
        }

        for(int i = 0; i < dateList.size(); i++)
        {
            String date = dateList.get(i).toString();
            if(date.equals(day))
            {
                timeList.add(showInfoList.get(i).getShowDateTime().toLocalTime());
            }
        }
        System.out.println(timeList);
        return timeList;
    }
}
