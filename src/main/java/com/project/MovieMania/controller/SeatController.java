package com.project.MovieMania.controller;

import com.project.MovieMania.domain.QryResult;
import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.service.SeatService;
import com.project.MovieMania.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketingService ticketingService;

    @PostMapping("/check")
    public QryResult check(@RequestParam String seatRow,@RequestParam String seatColumn,@RequestParam Long userId,@RequestParam Long showInfoId)
    {
        int row = Integer.parseInt(seatRow);
        int column = Integer.parseInt(seatColumn);
        Long ticketId = 0L;
        List<TicketInfo> ticketInfo = ticketingService.findTicket(showInfoId,userId);
        if(!ticketInfo.isEmpty())
        {
            ticketId = ticketInfo.get(0).getId();
        }

        System.out.println(showInfoId+"상영정보"+userId);
        System.out.println(ticketInfo+"개수");

        System.out.println(seatService.checkSeat(ticketId,row,column));

        QryResult result = QryResult.builder()
                .count(seatService.checkSeat(ticketId,row,column))
                .status("OK")
                .build();

        return result;
    }

//    @PostMapping("/save")
//    public QryResult save(@RequestParam String seatRow,@RequestParam String seatColumn,@RequestParam Long userId,@RequestParam Long showInfoId)
//    {
//        int row = Integer.parseInt(seatRow);
//        int column = Integer.parseInt(seatColumn);
//        Long ticketId = 0L;
//        List<TicketInfo> ticketInfo = ticketingService.findTicket(showInfoId,userId);
//        if(!ticketInfo.isEmpty())
//        {
//            ticketId = ticketInfo.get(0).getId();
//        }
//
//
//        QryResult result = QryResult.builder()
//                .count(seatService.writeSeat(ticketId,row,column))
//                .status("OK")
//                .build();
//
//        return result;
//    }

    @PostMapping("/delete")
    public QryResult delete(@RequestParam String seatRow,@RequestParam String seatColumn,@RequestParam Long userId,@RequestParam Long showInfoId)
    {
        int row = Integer.parseInt(seatRow);
        int column = Integer.parseInt(seatColumn);
        List<TicketInfo> ticketInfo = ticketingService.findTicket(showInfoId,userId);

        Seat seat = seatService.findSeat(ticketInfo.get(0).getId(),row,column);

        QryResult result = QryResult.builder()
                .count(seatService.deleteSeat(seat))
                .status("OK")
                .build();

        return result;
    }



}
