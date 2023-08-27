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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketingService ticketingService;

    @PostMapping("/check")
    public List<QryResult> check(@RequestParam String seatRow,@RequestParam String seatColumn,
                           @RequestParam Long userId,@RequestParam Long showInfoId)
    {
        int row = Integer.parseInt(seatRow);
        int column = Integer.parseInt(seatColumn);
        Long ticketId = 0L;
        List<TicketInfo> ticketInfo = ticketingService.findTicket(showInfoId,userId);
        List<QryResult> resultList = new ArrayList<>();
        for(int i = 0 ; i < ticketInfo.size(); i++) {
            ticketId = ticketInfo.get(i).getId();
            QryResult result = QryResult.builder()
                    .count(seatService.checkSeat(ticketId,row,column))
                    .status("OK")
                    .build();
            resultList.add(result);
        }



        return resultList;
    }




}
