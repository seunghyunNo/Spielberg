package com.project.MovieMania.service;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.repository.MyPageRepository;
import com.project.MovieMania.repository.QuestionRepository;
import com.project.MovieMania.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class MyPageServiceImpl implements MyPageService {

    private MyPageRepository myPageRepository;

    private QuestionRepository questionRepository;

    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;

    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;

    @Override
    public List<TicketInfo> TICKET_INFOS(Model model, Integer page, long id) {
        return null;
    }


//
//    @Override
//    public List<Question> QuestionList(Model model, Integer page, long id) {
//
//        if(page==null||page<1){
//            page=1;
//        }
//        HttpSession session = U.getSession();
//        Integer writePage = (Integer) session.getAttribute("writePage");
//        Integer pageRows = (Integer) session.getAttribute("pageRows");
//
//        if(writePage==null){
//            writePage=WRITE_PAGES;
//        }
//        if(pageRows==null){
//            pageRows=PAGE_ROWS;
//        }
//
//        session.setAttribute("page",page);
//
//        long count = myPageRepository.findById(id).getId();
//        int totalPage=(int) Math.ceil(count/(double)pageRows);
//
//        if(page>totalPage){
//            page=totalPage;
//        }
//
//        int fromRow = (page-1)*pageRows;
//        if(page==0){
//            fromRow=0;
//        }
//
//        int start=(((page-1)/writePage)*writePage)+1;
//        int end = start+writePage-1;
//        if(end>=totalPage)end =totalPage;
//
//        model.addAttribute("count",count);
//        model.addAttribute("page",page);
//        model.addAttribute("totalPage",totalPage);
//        model.addAttribute("pageRows",pageRows);
//
//        model.addAttribute("url",U.getRequest().getRequestURI());
//        model.addAttribute("writePage",writePage);
//        model.addAttribute("start",start);
//        model.addAttribute("end",end);


        // 여기 아직 모르겠음

//        List<Question> list = myPageRepository.findByFromRowAndPageRowsAndId(fromRow,pageRows,id);

//        return list;
//    }
}
