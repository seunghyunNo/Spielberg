package com.project.MovieMania.controller;

import com.project.MovieMania.config.PrincipalDetails;
import com.project.MovieMania.domain.Authority;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.UserStatus;
import com.project.MovieMania.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	public AdminController(){
		System.out.println("AdminController() 생성");
	}
	
	private AdminMovieService movieService;
	private AdminShowInfoService showInfoService;
	private TheaterService theaterService;
	private AdminReportService reportService;
	
	private AdminUserService userService;
	
	@Autowired
	public void setMovieService(AdminMovieService movieService){
		this.movieService = movieService;
	}
	@Autowired
	public  void setShowInfoService(AdminShowInfoService showInfoService){ this.showInfoService = showInfoService;	}
	@Autowired
	public void setTheaterService(TheaterService theaterService){ this.theaterService = theaterService; }
	@Autowired
	public void setReportService(AdminReportService reportService){ this.reportService = reportService; }
	@Autowired
	public void setUserService(AdminUserService userService){ this.userService = userService; }
	
	
	@RequestMapping("/")
	public String adminHome(){
		return "redirect:/admin/movie";
	}
	
	// -------- movie ----------
	@RequestMapping("/movie")
	public String movieList(Model model){
		if(userService.checkAuthority()){
			model.addAttribute("list", movieService.list());
			return "admin/movie/list";
		}else {
			return "admin/noAuthority";
		}
	}
	
	@GetMapping("/movie/register")
	public void movieRegister(
			@RequestParam(required = false, name = "movieCode") String movieCode
			, Model model
	){
			model.addAttribute("movieCode", movieCode);
	}
	
	@PostMapping("/movie/register")
	public String movieRegisterOk(Movie movie, Model model){
		model.addAttribute("result", movieService.register(movie));
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
				return "admin/none";
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
			return "admin/none";
		}
		return "admin/movie/deleteOk";
	}
	
	@GetMapping("/movie/update/{id}")
	public String movieUpdate(@PathVariable long id, Model model){
		try{
			Movie movie = movieService.detail(id);
			model.addAttribute("movie", movie);
		}catch (RuntimeException e){
			return "admin/none";
		}
		return "admin/movie/update";
	}
	
	@PostMapping("/movie/update")
	public String movieUpdateOk(Movie movie, Model model){
		model.addAttribute("result", movieService.update(movie));
		model.addAttribute("movieId", movie.getId());
		return	"admin/movie/updateOK";
	}
	
	// -------- showInfo -------------
	@GetMapping("/show")
	public String showList(
			Model model,
			@RequestParam(required = false, name = "selectStatus") String status
	){
		if(status == null || status.trim().isEmpty()){
			status = "ALL";
		}
		model.addAttribute("list", showInfoService.list(status));
		model.addAttribute("status", status);
		return "admin/show/list";
	}
	
	@GetMapping("/show/register")
	public void showRegister(){
	}
	
	@PostMapping("/show/register")
	public String showRegisterOk(ShowInfo showInfo, Model model){
		model.addAttribute("result", showInfoService.register(showInfo));
		return "admin/show/registerOk";
	}
	
	@GetMapping("/show/update/{id}")
	public String showUpdate(@PathVariable("id") Long id, Model model){
		try{
			ShowInfo showInfo = showInfoService.detail(id);
			model.addAttribute("show", showInfo);
			model.addAttribute("theaterInfo", theaterService.findById(showInfo.getTheater().getId()));
			model.addAttribute("showTimeHour", showInfo.getShowDateTime().getHour());
			model.addAttribute("showTimeMin", showInfo.getShowDateTime().getMinute());
			model.addAttribute("showDate", showInfo.getShowDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		}catch (RuntimeException e){
			return "admin/none";
		}
		return "admin/show/update";
	}
	
	@PostMapping("/show/update")
	public String showUpdateOk(ShowInfo showInfo, Model model){
		model.addAttribute("result", showInfoService.update(showInfo));
		return "admin/show/updateOk";
	}
	
	@PostMapping("/show/delete")
	public String showDelete(@RequestParam("showInfoId") Long id, Model model){
		model.addAttribute("result", showInfoService.delete(id));
		return "admin/show/deleteOk";
	}
	
	// --------- report ---------------
	@GetMapping("/report")
	public String reportList(){
		return "admin/report/list";
	}
	
	// -------- user -----------------
	
	@GetMapping("/user")
	public String userList(){
		return "admin/user/list";
	}
	
	
}
