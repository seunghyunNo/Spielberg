package com.project.MovieMania.service;

import com.project.MovieMania.domain.Seat;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        seatRepository.saveAndFlush(seat);

        if(seat != null)
        {
            result = 1;
        }


        return result;
    }

    @Override
    public int checkSeat(Long ticketId,Integer seatRow, Integer seatColumn) {

        List<Seat> seat = seatRepository.findBySeatRowAndSeatColumn(seatRow,seatColumn);
        Seat useSeat = new Seat();


        if(!seat.isEmpty()) {
            for (int i = 0; i < seat.size(); i++) {
                if (seat.get(i).getTicketInfo().getId() == ticketId) {
                    useSeat = seat.get(i);
                }
            }
        }

        if(useSeat.getId() == null)
        {
            return 0;
        }

        return 1;
    }


}
