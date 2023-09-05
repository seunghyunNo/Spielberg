package com.project.MovieMania.controller;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.*;
import com.project.MovieMania.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        model.addAttribute("cinemas",theaterService.cinemaSet(movie_id));
        model.addAttribute("dates",theaterService.dateList(movie_id));
        model.addAttribute("times",theaterService.timeList(movie_id));

        return "ticket/theater";
    }

    @PostMapping("/theater/{movie_id}")
    public String selectTheater(@PathVariable Long movie_id, @RequestParam String cinemaName,
                                @RequestParam String date, @RequestParam String time, Model model) {

        time = " " + time;
        String dateTime = date.concat(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime showDateTime = LocalDateTime.parse(dateTime, formatter);
        ShowInfo showInfo = showInfoService.findShowInfo(movie_id, cinemaName, showDateTime, model);

        return "redirect:/ticket/ticketing/" + showInfo.getId();
    }

    @GetMapping("/ticketing/{showInfoId}")
    public String getTicket(@PathVariable Long showInfoId, Model model) {
        Seat seat = new Seat();
        model.addAttribute("writeSeat", seat);

        ShowInfo showInfo = showInfoService.findById(showInfoId, model);
        model.addAttribute("theaterNum", showInfo.getTheater().getTheaterNum());

        int seatMaxRow = showInfo.getTheater().getMaxSeatRow();
        List<Integer> seatRowList = new ArrayList<>();
        for (int i = 1; i <= seatMaxRow; i++) {
            seatRowList.add(i);
        }
        model.addAttribute("seatMaxRow", seatRowList);
        model.addAttribute("maxRow", seatMaxRow);
        int seatMaxColumn = showInfo.getTheater().getMaxSeatColumn();
        List<Integer> seatColumnList = new ArrayList<>();
        for (int i = 1; i <= seatMaxColumn; i++) {
            seatColumnList.add(i);
        }
        model.addAttribute("seatMaxColumn", seatColumnList);

        try{
            PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDetails.getUser();
            Long id = user.getId();
            model.addAttribute("userId", id);
        } catch (Exception e){
            model.addAttribute("userId", null);
        }

        return "ticket/ticketing";
    }

    @PostMapping("/ticketing/{showInfoId}")
    public String ticketing(@PathVariable Long showInfoId,
                            Integer adult, Integer student,
                            @RequestParam List<Integer> seatRow, @RequestParam List<Integer> seatColumn,Long userId, Model model) {
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


        for (int y = 0; y < seatColumn.size(); y++) {
            for (int x = 0; x < seatRow.size(); x++) {
                if (seatColumn.get(y) == 0) {
                    seatColumn.remove(y);
                }
                if (seatRow.get(x) == 0) {
                    seatRow.remove(x);
                }
            }
        }


        if (adult > 0 && student == 0) {         // 성인만 선택
            for (int i = 0; i < adult; i++) {
                priceInfo = priceService.checkAdultNum();
                for (int x = 0; x < seatColumn.size(); x++) {
                    for (int y = 0; y < seatRow.size(); y++) {
                        ticketInfo = ticketingService.writeTicket(showInfoId, userId, priceInfo.getId());
                        seatService.writeSeat(ticketInfo, seatRow.get(y), seatColumn.get(x));
                    }
                }
            }
        } else if (student > 0 && adult == 0) {    // 학생만 선택
            for (int i = 0; i < student; i++) {
                priceInfo = priceService.checkStudentNum();
                for (int x = 0; x < seatColumn.size(); x++) {
                    for (int y = 0; y < seatRow.size(); y++) {
                        ticketInfo = ticketingService.writeTicket(showInfoId, userId, priceInfo.getId());
                        seatService.writeSeat(ticketInfo, seatRow.get(y), seatColumn.get(x));
                    }
                }
            }
        } else if (student > 0 && adult > 0)   // 성인 학생 각각 1명이상 선택
        {
            int total = student + adult;
            int count = 0;
            int adultCnt = 0;
            int studentCnt = 0;
            for (int x = 0; x < seatColumn.size(); x++) {
                for (int y = 0; y < seatRow.size(); y++) {
                    if (adultCnt != adult) {
                        priceInfo = priceService.checkAdultNum();
                        ticketInfo = ticketingService.writeTicket(showInfoId, userId, priceInfo.getId());
                        seatService.writeSeat(ticketInfo, seatRow.get(y), seatColumn.get(x));
                        adultCnt++;
                    } else if (student != studentCnt) {
                        priceInfo = priceService.checkStudentNum();
                        ticketInfo = ticketingService.writeTicket(showInfoId, userId, priceInfo.getId());
                        seatService.writeSeat(ticketInfo, seatRow.get(y), seatColumn.get(x));
                        studentCnt++;
                    }
                }
            }


        }



        ShowInfo showInfo = showInfoService.findById(showInfoId, model);



        return "redirect:/ticket/purchase/" + showInfo.getId();
    }

    @GetMapping("/purchase/{showInfoId}")
    public String getPurchase(@PathVariable Long showInfoId, Model model) {
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDetails.getUser();

        List<TicketInfo> buyList = ticketingService.findBuyTicket(showInfoId, user.getId());
        int cost = 0;
        int adultCnt = 0;
        int studentCnt = 0;
        int ticketCnt = buyList.size();
        for (int i = 0; i < buyList.size(); i++) {
            if (buyList.get(i).getPriceInfo().getName().equals("성인")) {
                cost += buyList.get(i).getPriceInfo().getPrice();
                adultCnt++;
            }
            if (buyList.get(i).getPriceInfo().getName().equals("학생")) {
                cost += buyList.get(i).getPriceInfo().getPrice();
                studentCnt++;
            }
        }

        ShowInfo showInfo = showInfoService.findById(showInfoId, model);
        model.addAttribute("cost", cost);
        model.addAttribute("movieName", showInfo.getMovie().getTitle());
        model.addAttribute("adultCnt", adultCnt);
        model.addAttribute("studentCnt", studentCnt);
        model.addAttribute("ticketCnt", ticketCnt);
        model.addAttribute("showInfoId",showInfoId);


        return "ticket/purchase";
    }

    @GetMapping("/complete/{showInfoId}")
    public String complete(@PathVariable Long showInfoId,Model model)
    {
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDetails.getUser();

        List<TicketInfo> ticketList = ticketingService.findTicket(showInfoId,user.getId(),model);

        String showTime = ticketList.get(0).getShowInfo().getShowDateTime().toLocalDate().toString() +"일"+ticketList.get(0).getShowInfo().getShowDateTime().toLocalTime().toString();

        String theaterNum = ticketList.get(0).getShowInfo().getTheater().getTheaterNum() + "관";

        model.addAttribute("movieName",ticketList.get(0).getShowInfo().getMovie().getTitle());
        model.addAttribute("showTime",showTime);
        model.addAttribute("theaterNum",theaterNum);
        return "ticket/complete";
    }
}
