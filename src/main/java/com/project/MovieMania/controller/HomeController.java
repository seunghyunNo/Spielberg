package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.service.HomeService;
import com.project.MovieMania.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping("/")
    public String home(Integer page, Model model){
        model.addAttribute("movieList", homeService.movieList(page, model));
        return "/home";
    }


    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows) {
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/list?=page="+ page;
    }



    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Movie> searchList = homeService.search(keyword);
        model.addAttribute("movieList", searchList);
        return "/home/search";
    }

//    @GetMapping("/search")
//    public String search(@RequestParam("keyword") String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Movie> searchList = homeService.search(keyword, pageable);
//        model.addAttribute("movieList", searchList);
//        return "/home";
//    }


}
