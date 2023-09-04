package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.Gender;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.service.QuestionService;
import com.project.MovieMania.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public void list(Integer page, Model model) {

        model.addAttribute("list", questionService.list(page,  model));

    }


    @GetMapping("/write")
    public void write(Model model){

        User user = U.getLoggedUser();

        model.addAttribute("user_id", user.getId());
        model.addAttribute("username", user.getUsername());

    }


    @PostMapping("/write")
    public  String writeOk(Question question,
                           Model model){

        int write = questionService.write(question);
        model.addAttribute("result", write);

        return "question/writeOk";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model){


        model.addAttribute("question", questionService.detail(id));

        return "question/detail";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable long id, Model model){

        model.addAttribute("question", questionService.selectById(id));
        return "question/update";

    }

    @PostMapping("/update")
    public String updateOk(
            Question question,
            Model model
    ){

        model.addAttribute("result", questionService.update(question));
        return "question/updateOk";
    }

    @PostMapping("/answer")
    public String answerOk(Question question,
                           Model model){

        model.addAttribute("result", questionService.answer(question));
        return "question/answerOk";
    }

    @PostMapping("/delete")
    public String delete(long id, Model model){
        model.addAttribute("result", questionService.delete(id));
        return "question/deleteOk";
    }


    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows) {
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/question/list?=page="+ page;
    }


}
