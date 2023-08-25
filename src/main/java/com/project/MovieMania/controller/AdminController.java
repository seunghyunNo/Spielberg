package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.service.AdminMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	public AdminController(){
		System.out.println("AdminController() 생성");
	}
	
	private AdminMovieService movieService;
	
	@Autowired
	public void setMovieService(AdminMovieService movieService){
		this.movieService = movieService;
	}
	
	@RequestMapping("/")
	public String adminHome(){
		return "redirect:/admin/movie";
	}
	
	@RequestMapping("/movie")
	public String movieList(Model model){
		model.addAttribute("list", movieService.list());
		return "admin/movie/list";
		
	}
	
	@GetMapping("/movie/register")
	public void movieRegister(){}
	
	@PostMapping("/movie/register")
	public String movieRegisterOk(Movie movie, Model model){
		model.addAttribute("result", movieService.save(movie));
		model.addAttribute("movieId", movie.getId());
		return "admin/movie/registerOk";
	}
	
	@GetMapping("/movie/register/api")
	public String apiRegister(){
		return "admin/movie/apiRegister";
	}
	
	@GetMapping("/movie/detail/{id}")
	public String movieDetail(@PathVariable Long id, Model model){
		if(id!=null){
			try{
				Movie movie = movieService.detail(id);
				model.addAttribute("movie", movie);
			}catch (RuntimeException e){
				return "admin/movie/none";
			}
		}
		return "admin/movie/detail";
	}
	
	@PostMapping("/movie/delete")
	public String movieDelete(@RequestParam("movieId") Long movieId, Model model){
		try{
			int result = movieService.delete(movieId);
			model.addAttribute("result", result);
		} catch (RuntimeException e){
			return "admin/movie/none";
		}
		return "admin/movie/deleteOk";
	}
	
	@GetMapping("/movie/update/{id}")
	public String movieUpdate(@PathVariable long id, Model model){
		try{
			Movie movie = movieService.detail(id);
			model.addAttribute("movie", movie);
		}catch (RuntimeException e){
			return "admin/movie/none";
		}
		return "admin/movie/update";
	}
	
	@PostMapping("/movie/update")
	public String movieUpdateOk(Movie movie, Model model){
		model.addAttribute("result", movieService.save(movie));
		model.addAttribute("movieId", movie.getId());
		return	"admin/movie/updateOK";
	}
	
}
