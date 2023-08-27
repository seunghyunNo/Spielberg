package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findBySeatRowAndSeatColumn(Integer seatRow, Integer seatColumn);
}
