package com.project.MovieMania.service;

import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.TicketInfo;

import java.util.List;

public interface SeatService {

    int writeSeat(TicketInfo ticketInfo, Integer seatRow, Integer seatColumn);

    int checkSeat(Long ticketId, Integer seatRow, Integer seatColumn);

}
