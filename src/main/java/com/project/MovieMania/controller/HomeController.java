package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private HomeService homeService;

    public HomeController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @RequestMapping("/")
    public String home(Integer page, Model model){
        model.addAttribute("movieList", homeService.movieList(page, model));

        return "/home";
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
