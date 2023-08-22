package com.project.MovieMania.service;

import com.project.MovieMania.domain.Seat;

public interface SeatService {

    Seat findSeat(Long ticketId);
}
