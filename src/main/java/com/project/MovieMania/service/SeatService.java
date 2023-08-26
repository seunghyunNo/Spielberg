package com.project.MovieMania.service;

import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.TicketInfo;

public interface SeatService {

    int writeSeat(TicketInfo ticketInfo, Integer seatRow, Integer seatColumn);

    int checkSeat(Integer seatRow,Integer seatColumn);
    Seat findSeat(Integer seatRow,Integer seatColumn);

    int deleteSeat(Seat seat);
}
