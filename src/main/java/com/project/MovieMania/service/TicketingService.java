package com.project.MovieMania.service;

import com.project.MovieMania.domain.TicketInfo;

public interface TicketingService {

    TicketInfo writeTicket(Long showInfoId,Long userId,Long priceId);
}
