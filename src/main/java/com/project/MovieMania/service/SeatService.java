package com.project.MovieMania.service;

import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.TicketInfo;

public interface SeatService {

    int writeSeat(TicketInfo ticketInfo, Integer seatRow, Integer seatColumn);

    int findSeat(Integer seatRow,Integer seatColumn);
}
