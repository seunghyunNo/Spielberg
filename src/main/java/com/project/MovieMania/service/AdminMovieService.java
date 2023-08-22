package com.project.MovieMania.service;

import com.project.MovieMania.domain.Movie;
import org.springframework.stereotype.Service;

public interface AdminMovieService {
	int register(Movie movie);
	
}
