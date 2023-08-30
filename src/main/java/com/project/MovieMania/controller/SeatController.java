package com.project.MovieMania.controller;

import com.project.MovieMania.domain.QryResult;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.service.SeatService;
import com.project.MovieMania.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketingService ticketingService;

    @PostMapping("/check")
    public QryResult check(@RequestParam String seatRow,@RequestParam String seatColumn)
    {
        int row = Integer.parseInt(seatRow);
        int column = Integer.parseInt(seatColumn);


        QryResult result = QryResult.builder()
                .count(seatService.findSeat(row,column))
                .status("OK")
                .build();

        return result;
    }

    @PostMapping("/save")
    public QryResult save(@RequestParam String seatRow,@RequestParam String seatColumn,@RequestParam Long userId,@RequestParam Long showInfoId,@RequestParam Long priceId)
    {
        int row = Integer.parseInt(seatRow);
        int column = Integer.parseInt(seatColumn);
        TicketInfo ticketInfo = ticketingService.writeTicket(showInfoId,userId,priceId);

        QryResult result = QryResult.builder()
                .count(seatService.writeSeat(ticketInfo,row,column))
                .status("OK")
                .build();

        return result;
    }



}
