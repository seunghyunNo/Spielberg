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

import java.util.List;
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
        List<TicketInfo> ticket = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);
        Random random = new Random();
        String ticketCode = "";


        if(!ticket.isEmpty()) {
            ticketCode = ticket.get(0).getTicketCode();
        }
//        else
//        {
//            for(int i = 0 ; i < 7; i ++)
//            {
//                char code = (char)((Math.random() * 26)+65);
//                ticketCode +=code;
//            }
//            List<TicketInfo> codeCheck = ticketInfoRepository.findByTicketCode(ticketCode);
//            if(!codeCheck.isEmpty())
//            {
//                for(int i = 0 ; i < 7; i ++)
//                {
//                    char code = (char)((Math.random() * 26)+65);
//                    ticketCode +=code;
//                }
//            }
//        }



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
        ShowInfo showInfo = showinfoRepoisotry.findById(showInfoId).orElse(null);
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
        ShowInfo showInfo = showinfoRepoisotry.findById(showInfoId).orElse(null);
        List<TicketInfo> ticketInfo = ticketInfoRepository.findByShowInfo(showInfo);
        return ticketInfo;
    }

//    @Override
//    public List<TicketInfo> findAll() {
//        List<TicketInfo> allTicket = ticketInfoRepository.findAll();
//        String ticketCode = "";
//        boolean check = false;
//        for(int i = 0; i < allTicket.size(); i++)
//        {
//            ShowInfo showInfo = allTicket.get(i).getShowInfo();
//            User user = allTicket.get(i).getUser();
//            List<TicketInfo> checkTicket = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);
//            if(allTicket.get(i).getTicketCode().isEmpty())
//            {
//                // 해당 유저와 상영정보를 가진 티켓이 코드를 가지지않았는지 확인
//                for(int y = 0; y < checkTicket.size(); y++)
//                {
//                    for(int j = 0; j < checkTicket.size(); j++)
//                    {
//                        if(!checkTicket.get(j).getTicketCode().isEmpty())
//                        {
//                            check = true;
//                            break;
//                        }
//                    }
//                    if(!check) {
//                        if (!checkTicket.get(y).getTicketCode().isEmpty()) {
//                            ticketCode = checkTicket.get(y).getTicketCode();
//                            break;
//                        } else {
//                            TicketInfo ticket = TicketInfo.builder()
//
//                                    .build();
//                        }
//                    }
//                }
//
//                // 티켓코드칸이 비어있다면 티켓코드 랜덤문자열 7자리 작성
//                if(check) {
//                    for (int x = 0; x < 7; x++) {
//                        char code = (char) ((Math.random() * 26) + 65);
//                        ticketCode += code;
//                    }
//                    List<TicketInfo> codeCheck = ticketInfoRepository.findByTicketCode(ticketCode);
//                    if (!codeCheck.isEmpty()) {
//                        for (int x = 0; x < 7; x++) {
//                            char code = (char) ((Math.random() * 26) + 65);
//                            ticketCode += code;
//                        }
//                    }
//                }
//            }
//        }
//        return allTicket;
//    }
}
