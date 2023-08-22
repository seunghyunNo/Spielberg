package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TheaterRepository;
import com.project.MovieMania.service.SeatService;
import com.project.MovieMania.service.ShowInfoService;
import com.project.MovieMania.service.TheaterService;
import com.project.MovieMania.service.TicketingService;
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

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketingService ticketingService;

    @Autowired
    private ShowInfoService showInfoService;

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
        showInfoService.writeShowInfo(movie_id,cinemaName,showDateTime,model);
    }

    @PostMapping("/ticketing")
    public String ticketing(@RequestParam Long show_info_id,@RequestParam Long user_id,@RequestParam Long price_id,
                            Integer seatRow,Integer seatColumn, Model model)
    {
        TicketInfo ticketInfo = ticketingService.writeTicket(show_info_id,user_id,price_id);


        int result = seatService.findSeat(seatRow,seatColumn);
        if(result == 0)
        {
            model.addAttribute("full",seatService.writeSeat(ticketInfo,seatRow,seatColumn));
        }
        model.addAttribute("seatMaxRow",showInfoService.findById(show_info_id).getTheater().getMaxSeatRow());
        model.addAttribute("seatMaxColumn",showInfoService.findById(show_info_id).getTheater().getMaxSeatColumn());
        model.addAttribute("theaterNum",showInfoService.findById(show_info_id).getTheater().getTheaterNum());

        return "ticket/ticketing";
    }

}
