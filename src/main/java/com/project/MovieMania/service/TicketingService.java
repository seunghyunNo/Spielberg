package com.project.MovieMania.service;

import com.project.MovieMania.domain.TicketInfo;
import org.springframework.ui.Model;

import java.util.List;

public interface TicketingService {

    TicketInfo writeTicket(Long showInfoId,Long userId,Long priceId);

    List<TicketInfo> findTicket(Long showInfoId, Long userId);

    List<TicketInfo> findShowInfoTicket(Long showInfoId);

    List<TicketInfo> findBuyTicket(Long showInfoId,Long userId);

    List<TicketInfo> findMyTicketInfo(Long id);

}
