package com.project.MovieMania.controller;


import com.project.MovieMania.domain.*;
import com.project.MovieMania.domain.DTO.MovieDTO;
import com.project.MovieMania.domain.DTO.TheaterDTO;
import com.project.MovieMania.repository.TheaterRepository;
import com.project.MovieMania.service.AdminMovieService;
import com.project.MovieMania.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminRest")
public class AdminRestController {
	
	private AdminMovieService movieService;
	private TheaterService theaterService;
	
	@Autowired
	public void setMovieService(AdminMovieService movieService) {
		this.movieService = movieService;
	}
	@Autowired
	public void setTheaterService(TheaterService theaterService){ this.theaterService = theaterService; }
	
	@PostMapping("/movieList")
	public QryMovieList listMovie(){
		QryMovieList movieList = new QryMovieList();
		List<Movie> list = movieService.list();
		
		List<MovieDTO> listDTO = new ArrayList<>();
		
		list.forEach(element -> {
			MovieDTO movieDTO = new MovieDTO(element.getId(), element.getMovieCode(), element.getTitle());
			listDTO.add(movieDTO);
		});
		
		movieList.setStatus("OK");
		movieList.setCount(listDTO.size());
		movieList.setList(listDTO);
		return movieList;
	}
	
	@PostMapping("/cinemaList")
	public QryCinemaList listCinema(){
		QryCinemaList list = new QryCinemaList();
		
		List<Cinema> cinemas = theaterService.cinemaList();
		list.setCount(cinemas.size());
		list.setStatus("OK");
		list.setCinemas(cinemas);
		
		return list;
	}
	
	@Autowired
	private TheaterRepository theaterRepository;
	
	@PostMapping("/theaterList")
	public QryTheaterList listTheater(@RequestParam("cinemaId") Long cinemaId){
		QryTheaterList list = new QryTheaterList();
		List<Theater> theaters = theaterRepository.findTheaterByCinemaId(cinemaId);
		
		List<TheaterDTO> theaterDTOS = new ArrayList<>();
		theaters.forEach(element -> {
			TheaterDTO theaterDTO = TheaterDTO.builder()
					.id(element.getId())
					.theaterNum(element.getTheaterNum())
					.build();
			theaterDTOS.add(theaterDTO);
		});
		list.setTheaterDTOs(theaterDTOS);
		list.setCount(theaterDTOS.size());
		list.setStatus("OK");
		return list;
	}
	
}
