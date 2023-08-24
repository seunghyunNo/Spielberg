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
import java.util.ArrayList;
import java.util.List;

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
    public String selectTheater(@PathVariable Long movie_id, @RequestParam String cinemaName,@RequestParam String date,@RequestParam String time,Model model)
    {
        time = " "+time;
        String dateTime = date.concat(time);
        System.out.println("영화번호"+movie_id+"이름"+cinemaName+"날짜"+date+"시간"+time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime showDateTime = LocalDateTime.parse(dateTime,formatter);
        ShowInfo showInfo =showInfoService.writeShowInfo(movie_id,cinemaName,showDateTime,model);

        return "redirect:/ticket/ticketing/"+showInfo.getId();
    }

    @GetMapping("/ticketing/{showInfoId}")
    public String getTicket(@PathVariable Long showInfoId,Model model)
    {
        ShowInfo showInfo = showInfoService.findById(showInfoId,model);
        System.out.println("TICKET");
        int seatMaxRow = showInfo.getTheater().getMaxSeatRow();
        System.out.println("좌석최대로우"+seatMaxRow);
        List<Integer> seatRowList = new ArrayList<>();
        for(int i = 1; i <= seatMaxRow; i++)
        {
            seatRowList.add(i);
        }
        model.addAttribute("seatMaxRow",seatRowList);
        int seatMaxColumn = showInfo.getTheater().getMaxSeatColumn();
        System.out.println("좌석최대컬럼"+seatMaxColumn);
        List<Integer> seatColumnList = new ArrayList<>();
        for(int i = 1 ; i <= seatMaxColumn; i++)
        {
            seatColumnList.add(i);
        }
        model.addAttribute("seatMaxColumn",seatColumnList);

        return "ticket/ticketing";
    }

    @PostMapping("/ticketing/{showInfoId}")
    public String ticketing(@PathVariable Long showInfoId,@RequestParam Long userId,@RequestParam Long priceId,
                            Integer seatRow,Integer seatColumn, Model model)
    {
        TicketInfo ticketInfo = ticketingService.writeTicket(showInfoId,userId,priceId);


        int result = seatService.findSeat(seatRow,seatColumn);
        if(result == 0)
        {
            model.addAttribute("full",seatService.writeSeat(ticketInfo,seatRow,seatColumn));
        }

        // 로그인한 유저 정보 모델로 넘기기 TODO
        model.addAttribute("showInfoId",ticketInfo.getShowInfo().getId());
        ShowInfo showInfo = showInfoService.findById(showInfoId,model);


        model.addAttribute("theaterNum",showInfo.getTheater().getTheaterNum());

        return "ticket/ticketing";
    }

}
