package com.project.MovieMania.controller;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.User;
//import com.project.MovieMania.service.MyPageService;
import com.project.MovieMania.service.MyPageService;
import com.project.MovieMania.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class MyPageController {
    @Autowired
    private MyPageService myPageService;

    @GetMapping("/myPage/myTicket")
    public void myTicket(Integer page,Model model){
        PrincipalDetails userDetails =(PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDetails.getUser();
        Long id = user.getId();
        String username = user.getUsername();

        model.addAttribute("id",id);
        model.addAttribute("username",username);
    }



    // 페이징 리스트만 보여주면 되나???
    @PostMapping("myPage/myTicket")
    public String myTicketInfo(
            @RequestParam("ticket_code") String ticket_code,
            @RequestParam("price") int price,
            Model model
    ){
        return "";
    }

























//
//    @GetMapping("/myPage/myQuestion")
//    public void myQuestion(Integer page, Model model){
//        PrincipalDetails userDetails =(PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        User user = userDetails.getUser();
//        Long id = user.getId();
//        String username = user.getUsername();
//
//        model.addAttribute("id",id);
//        model.addAttribute("username",username);
//
//        model.addAttribute("list",myPageService.QuestionList(model,page,id));
//    }
//
//    @PostMapping("QuestionPageRows")
//    public String questionPageRows(Integer page,Integer pageRows){
//        U.getSession().setAttribute("pageRows",pageRows);
//        return "redirect:/user/myPage/myQuestion?page="+page;
//    }


}
