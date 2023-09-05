package com.project.MovieMania.service;
import com.project.MovieMania.domain.*;
import com.project.MovieMania.repository.PriceInfoRepository;
import com.project.MovieMania.repository.ShowInfoRepository;
import com.project.MovieMania.repository.TicketInfoRepository;
import com.project.MovieMania.repository.UserRepository;
import com.project.MovieMania.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TicketingServiceImpl implements TicketingService{

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

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
    public List<TicketInfo> findTicket(Long showInfoId, Long userId,Model model) {
        User user = userRepository.findById(userId).orElse(null);
        ShowInfo showInfo = showInfoRepository.findById(showInfoId).orElse(null);
        List<TicketInfo> ticketInfo = ticketInfoRepository.findByUserAndShowInfo(user,showInfo);

        boolean check = true;
        int count = 0;
        String ticketCode = "";

        for(int i = 0; i < ticketInfo.size(); i++)
        {
            if(!ticketInfo.get(i).getTicketCode().isEmpty())
            {
                check = false;
                ticketCode = ticketInfo.get(i).getTicketCode();
            }
            else
            {
                count++;
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
                    check = false;
                }
                else
                {
                    ticketInfo.get(i).setTicketCode(ticketCode);
                    TicketInfo ticket = ticketInfo.get(i);
                    ticketInfoRepository.saveAndFlush(ticket);
                }
            }
        String peopleCnt = count + "인";
        model.addAttribute("peopleCnt",peopleCnt);
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

    @Override
    public List<TicketInfo> findMyTicketInfo(Long id) {

        User user = userRepository.findById(id).orElseThrow();

        List<TicketInfo> ticketInfos = ticketInfoRepository.findByUser(user);

        return ticketInfos;
    }





}
