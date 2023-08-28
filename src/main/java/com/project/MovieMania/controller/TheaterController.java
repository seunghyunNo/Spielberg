package com.project.MovieMania.controller;

import com.project.MovieMania.domain.PriceInfo;
import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.repository.CinemaRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TheaterRepository;
import com.project.MovieMania.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private PriceService priceService;

    @GetMapping("/theater/{movie_id}")
    public String theater(@PathVariable Long movie_id,Model model)
    {
        model.addAttribute("movieId",movie_id);
//        model.addAttribute("theaterNum",theaterService.findById(theater_id).getTheaterNum());

        model.addAttribute("cinemas",theaterService.cinemaList());
        model.addAttribute("dates",theaterService.dateList());
        model.addAttribute("times",theaterService.timeList());

        return "ticket/theater";
    }

    @PostMapping("/theater/{movie_id}")
    public String selectTheater(@PathVariable Long movie_id, @RequestParam String cinemaName,
            @RequestParam String date,@RequestParam String time,Model model)
    {

        time = " "+time;
        String dateTime = date.concat(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime showDateTime = LocalDateTime.parse(dateTime,formatter);
        ShowInfo showInfo =showInfoService.findShowInfo(movie_id,cinemaName,showDateTime,model);

        return "redirect:/ticket/ticketing/"+showInfo.getId();
    }

    @GetMapping("/ticketing/{showInfoId}")
    public String getTicket(@PathVariable Long showInfoId,Model model)
    {
        Seat seat = new Seat();
        model.addAttribute("writeSeat",seat);

        ShowInfo showInfo = showInfoService.findById(showInfoId,model);
        model.addAttribute("theaterNum",showInfo.getTheater().getTheaterNum());

        int seatMaxRow = showInfo.getTheater().getMaxSeatRow();
        List<Integer> seatRowList = new ArrayList<>();
        for(int i = 1; i <= seatMaxRow; i++)
        {
            seatRowList.add(i);
        }
        model.addAttribute("seatMaxRow",seatRowList);
        model.addAttribute("maxRow",seatMaxRow);
        int seatMaxColumn = showInfo.getTheater().getMaxSeatColumn();
        List<Integer> seatColumnList = new ArrayList<>();
        for(int i = 1 ; i <= seatMaxColumn; i++)
        {
            seatColumnList.add(i);
        }
        model.addAttribute("seatMaxColumn",seatColumnList);

        return "ticket/ticketing";
    }

    @PostMapping("/ticketing/{showInfoId}")
    public String ticketing(@PathVariable Long showInfoId,
                            Integer adult,Integer student,
                            @RequestParam List<Integer> seatRow,@RequestParam List<Integer> seatColumn, Model model)
    {
        TicketInfo ticketInfo = new TicketInfo();
        PriceInfo priceInfo = new PriceInfo();
        Set<Integer> rowList = new HashSet<>();
        rowList.addAll(seatRow);
        seatRow.clear();
        Set<Integer> columnList = new HashSet<>();
        columnList.addAll(seatColumn);
        seatColumn.clear();

        seatRow.addAll(rowList);
        seatColumn.addAll(columnList);



        for(int y = 0; y < seatColumn.size(); y++)
        {
            for(int x = 0; x < seatRow.size(); x++)
            {
                if(seatColumn.get(y) == 0)
                {
                    seatColumn.remove(y);
                }
                if(seatRow.get(x) == 0)
                {
                    seatRow.remove(x);
                }
            }
        }

        System.out.println(seatRow);
        System.out.println(seatColumn);

        for (int i = 0; i < adult; i++)
        {
           priceInfo = priceService.checkAdultNum();
           for(int x = 0 ; x < seatColumn.size(); x++)
           {
               for(int y = 0 ; y < seatRow.size(); y++)
               {
                   ticketInfo = ticketingService.writeTicket(showInfoId,1L,priceInfo.getId());
                   seatService.writeSeat(ticketInfo,seatRow.get(y),seatColumn.get(x));
               }
           }

        }

        for(int i = 0; i < student; i++)
        {
            priceInfo = priceService.checkStudentNum();
            for(int x = 0 ; x < seatColumn.size(); x++)
            {
                for(int y = 0 ; y < seatRow.size(); y++)
                {
                    ticketInfo = ticketingService.writeTicket(showInfoId,1L,priceInfo.getId());
                    seatService.writeSeat(ticketInfo,seatRow.get(y),seatColumn.get(x));
                }
            }

        }


        // 로그인한 유저 정보 모델로 넘기기 TODO
        model.addAttribute("showInfoId",ticketInfo.getShowInfo().getId());
        ShowInfo showInfo = showInfoService.findById(showInfoId,model);


        model.addAttribute("theaterNum",showInfo.getTheater().getTheaterNum());

        return "ticket/purchase";
    }

}
