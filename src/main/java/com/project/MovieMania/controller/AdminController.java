package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	public AdminController(){
		System.out.println("AdminController() 생성");
	}
	
	@RequestMapping("/")
	public String adminHome(){
		return "redirect:/admin/movie";
	}
	
	@RequestMapping("/movie")
	public String movieList(){
		return "admin/movie/list";
	}
	
	@GetMapping("/movie/register")
	public void movieRegister(){}
	
	@PostMapping("/register")
	public String movieRegisterOk(Movie movie, Model model){
		
	
		return "";
	}
	
	@GetMapping("/movie/register/api")
	public String apiRegister(){
		return "admin/movie/apiRegister";
	}
	
}
