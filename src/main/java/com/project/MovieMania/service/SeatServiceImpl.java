package com.project.MovieMania.service;

import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService{

    private SeatRepository seatRepository;

    @Autowired
    public void setSeatRepository(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public int writeSeat(TicketInfo ticketInfo, Integer seatRow, Integer seatColumn) {
        int result = 0;

        Seat seat = Seat.builder()
                .ticketInfo(ticketInfo)
                .seatRow(seatRow)
                .seatColumn(seatColumn)
                .build();

        if(seat != null)
        {
            result = 1;
        }


        return result;
    }

    @Override
    public int findSeat(Integer seatRow, Integer seatColumn) {

        Seat seat = seatRepository.findBySeatRowAndSeatColumn(seatRow,seatColumn);

        if(seat == null)
        {
            return 0;
        }

        return 1;
    }
}
