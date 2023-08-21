package com.project.MovieMania.controller;

import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TheaterRepository;
import com.project.MovieMania.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/ticket")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping("/theater")
    public String theater(Model model)
    {
        return "ticket/theater";
    }

    @PostMapping("/theater")
    public void selectTheater(Long showInfoId,String cinemaName,String date,String time,Model model)
    {
        ShowInfo showInfo = theaterService.selectShowInfo(showInfoId);
        showInfo.getTheater().getCinema().setName(cinemaName);
        String dateTime = date.concat(time);
        LocalDateTime showDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        showInfo.setShowDateTime(showDateTime);
    }

}
