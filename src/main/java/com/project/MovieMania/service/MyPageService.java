package com.project.MovieMania.service;


import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.TicketInfo;
import org.springframework.ui.Model;

import java.util.List;

public interface MyPageService {

//    public List<Question> QuestionList(Model model,Integer page,long id);

    public List<TicketInfo> TICKET_INFOS(Model model,Integer page, long id);

}
