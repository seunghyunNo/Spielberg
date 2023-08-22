package com.project.MovieMania.service;

import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMovieServiceImpl implements AdminMovieService {
	
	private MovieRepository movieRepository;
	@Autowired
	public void setMovieRepository(MovieRepository movieRepository){
		this.movieRepository = movieRepository;
	}
	
	@Override
	public int register(Movie movie) {
		movieRepository.saveAndFlush(movie);
		return 1;
	}
}
