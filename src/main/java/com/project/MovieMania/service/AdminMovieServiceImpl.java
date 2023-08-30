package com.project.MovieMania.service;

import com.project.MovieMania.domain.DTO.MovieDTO;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminMovieServiceImpl implements AdminMovieService {
	
	private MovieRepository movieRepository;
	@Autowired
	public void setMovieRepository(MovieRepository movieRepository){
		this.movieRepository = movieRepository;
	}
	
	@Override
	public int save(Movie movie) {
		try{
			movieRepository.saveAndFlush(movie);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
	
	@Override
	public List<Movie> list() {
		return movieRepository.findAll();
	}
	
	@Override
	@Transactional
	public Movie detail(long id) throws RuntimeException {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException());
		return movie;
	}
	
	@Override
	public int delete(long id) throws RuntimeException {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException());
		try{
			movieRepository.delete(movie);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
	
	
}