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
	public int register(Movie movie) {
		try{
			movieRepository.saveAndFlush(movie);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
	
	@Override
	public int update(Movie movie) {
		try{
			Movie m = movieRepository.findById(movie.getId()).orElseThrow(() -> new RuntimeException());
			if(! movie.getMovieCode().isBlank()) m.setMovieCode(movie.getMovieCode());
			if(! movie.getTitle().isBlank() || movie.getTitle()!=null) {
				m.setTitle(movie.getTitle());
			} else {
				new RuntimeException();
			}
			if(! movie.getDirectors().isBlank() || movie.getDirectors() != null) m.setDirectors(movie.getDirectors());
			if(! movie.getActors().isBlank() || movie.getActors()!=null) m.setActors(movie.getActors());
			if(movie.getShowTime() != null)	m.setShowTime(movie.getShowTime());
			if(movie.getOpenDate() != null)	m.setOpenDate(movie.getOpenDate());
			if(! movie.getGenre().isBlank() || movie.getGenre() != null) m.setGenre(movie.getGenre());
			if(! movie.getRateGrade().isBlank() || movie.getRateGrade()!=null) m.setRateGrade(movie.getRateGrade());
			if(! movie.getImg().isBlank() || movie.getImg()!=null) m.setImg(movie.getImg());
			movieRepository.saveAndFlush(m);
		}catch (RuntimeException e){
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			System.out.println(movie);
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
	public int delete(long id){
		try{
			Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException());
			movieRepository.delete(movie);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
	
	
}