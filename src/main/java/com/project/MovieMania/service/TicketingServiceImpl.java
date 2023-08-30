package com.project.MovieMania.service;
import com.project.MovieMania.domain.PriceInfo;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.repository.PriceInfoRepository;
import com.project.MovieMania.repository.ShowInfoRepository;
import com.project.MovieMania.repository.TicketInfoRepository;
import com.project.MovieMania.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TicketingServiceImpl implements TicketingService{

    private ShowInfoRepository showInfoRepository;

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
    public void setShowinfoRepoisotry(ShowInfoRepository showInfoRepository) {
        this.showInfoRepository = showInfoRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TicketInfo writeTicket(Long showInfoId, Long userId,Long priceId) {
        User user = userRepository.findById(userId).orElse(null);
        ShowInfo showInfo = showInfoRepository.findById(showInfoId).orElse(null);
        PriceInfo priceInfo = priceInfoRepository.findById(priceId).orElse(null);
        List<TicketInfo> ticket = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);
        Random random = new Random();
        String ticketCode = "";

        TicketInfo ticketInfo = TicketInfo.builder()
                .showInfo(showInfo)
                .user(user)
                .priceInfo(priceInfo)
                .ticketCode(ticketCode)
                .build();

        ticketInfoRepository.saveAndFlush(ticketInfo);

        return ticketInfo;
    }

    @Override
    public List<TicketInfo> findTicket(Long showInfoId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        ShowInfo showInfo = showInfoRepository.findById(showInfoId).orElse(null);
        List<TicketInfo> ticketInfo = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);

        boolean check = true;
        String ticketCode = "";

        for(int i = 0; i < ticketInfo.size(); i++)
        {
            if(!ticketInfo.get(i).getTicketCode().isEmpty())
            {
                check = false;
                ticketCode = ticketInfo.get(i).getTicketCode();
            }
        }

            for (int i = 0; i < ticketInfo.size(); i++) {
                if(check) {
                    for (int x = 0; x < 7; x++) {
                        char code = (char) ((Math.random() * 26) + 65);
                        ticketCode += code;
                    }
                    List<TicketInfo> codeCheck = ticketInfoRepository.findByTicketCode(ticketCode);
                    if (!codeCheck.isEmpty()) {
                        for (int x = 0; x < 7; x++) {
                            char code = (char) ((Math.random() * 26) + 65);
                            ticketCode += code;
                        }
                    }
                    ticketInfo.get(i).setTicketCode(ticketCode);
                    TicketInfo ticket = ticketInfo.get(i);
                    ticketInfoRepository.saveAndFlush(ticket);
                }
                else
                {
                    ticketInfo.get(i).setTicketCode(ticketCode);
                    TicketInfo ticket = ticketInfo.get(i);
                    ticketInfoRepository.saveAndFlush(ticket);
                }
            }

        return ticketInfo;
    }

    @Override
    public List<TicketInfo> findShowInfoTicket(Long showInfoId) {
        ShowInfo showInfo = showInfoRepository.findById(showInfoId).orElse(null);
        List<TicketInfo> ticketInfo = ticketInfoRepository.findByShowInfo(showInfo);
        return ticketInfo;
    }

    @Override
    public List<TicketInfo> findBuyTicket(Long showInfoId, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        ShowInfo showInfo = showInfoRepository.findById(showInfoId).orElse(null);
        List<TicketInfo> ticketInfo = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);
        List<TicketInfo> buyList = new ArrayList<>();
        for(int i = 0; i < ticketInfo.size(); i++)
        {
            if(ticketInfo.get(i).getTicketCode().isEmpty())
            {
                buyList.add(ticketInfo.get(i));
            }
        }
        return buyList;
    }

}
