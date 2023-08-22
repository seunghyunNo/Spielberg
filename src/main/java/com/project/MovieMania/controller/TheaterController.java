package com.project.MovieMania.controller;

import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TheaterRepository;
import com.project.MovieMania.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/ticket")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/theater/{movie_id}")
    public String theater(@PathVariable Long movie_id,Model model)
    {
        model.addAttribute("movieId",movie_id);
        model.addAttribute("cinemas",theaterService.cinemaList());
        model.addAttribute("dates",theaterService.dateList());
        model.addAttribute("times",theaterService.timeList());

        return "ticket/theater";
    }

    @PostMapping("/theater/{movie_id}")
    public void selectTheater(@PathVariable Long movie_id, String cinemaName,String date,String time,Model model)
    {
        String dateTime = date.concat(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
        LocalDateTime showDateTime = LocalDateTime.parse(dateTime,formatter);
        ShowInfo showInfo = theaterService.selectShowInfo(movie_id,cinemaName,showDateTime);
        System.out.println(showInfo.getId());
        model.addAttribute("showInfoId",showInfo.getId());
    }

    @PostMapping("/ticketing")
    public String ticketing(@RequestParam Long movie_id,@RequestParam Long theater_id,@RequestParam Long user_id, Model model)
    {
        return "ticket/ticketing";
    }

}
