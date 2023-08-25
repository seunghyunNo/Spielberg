package com.project.MovieMania.controller;

import com.project.MovieMania.domain.QryResult;
import com.project.MovieMania.service.SeatService;
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
}
