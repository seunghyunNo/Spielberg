package com.project.MovieMania.service;
import com.project.MovieMania.domain.PriceInfo;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.repository.PriceInfoRepository;
import com.project.MovieMania.repository.ShowinfoRepoisotry;
import com.project.MovieMania.repository.TicketInfoRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TicketingServiceImpl implements TicketingService{

    private ShowinfoRepoisotry showinfoRepoisotry;

    private UserRepository userRepository;

    private PriceInfoRepository priceInfoRepository;

    private TicketInfoRepository ticketInfoRepository;

    @Autowired
    public void setTicketInfoRepository(TicketInfoRepository ticketInfoRepository) {
        this.ticketInfoRepository = ticketInfoRepository;
    }

    @Autowired
    public void setPriceInfoRepository(PriceInfoRepository priceInfoRepository) {
        this.priceInfoRepository = priceInfoRepository;
    }

    @Autowired
    public void setShowinfoRepoisotry(ShowinfoRepoisotry showinfoRepoisotry) {
        this.showinfoRepoisotry = showinfoRepoisotry;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TicketInfo writeTicket(Long showInfoId, Long userId,Long priceId) {
        User user = userRepository.findById(userId).orElse(null);
        ShowInfo showInfo = showinfoRepoisotry.findById(showInfoId).orElse(null);
        PriceInfo priceInfo = priceInfoRepository.findById(priceId).orElse(null);
        TicketInfo ticket = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);
        Random random = new Random();
        String ticketCode = "";

        if(ticket.getTicketCode().isEmpty()) {
            ticketCode = String.valueOf((char) ((int) random.nextInt(26)) + 'A');
        }
        else
        {
            ticketCode = ticket.getTicketCode();
        }

        TicketInfo ticketInfo = TicketInfo.builder()
                .showInfo(showInfo)
                .user(user)
                .priceInfo(priceInfo)
                .ticketCode(ticketCode)
                .build();

        return ticketInfo;
    }
}
