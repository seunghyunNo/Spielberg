package com.project.MovieMania.controller;

import com.project.MovieMania.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/question")
public class QuestionController {

    private QuestionService questionService;

    // 확인용
    public QuestionController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/list")
    public void list(Integer page, Model model) {
        model.addAttribute("list", questionService.list(page, model));
    }

}
