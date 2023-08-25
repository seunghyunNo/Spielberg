package com.project.MovieMania.service;

import com.project.MovieMania.domain.Movie;
import org.springframework.ui.Model;

import java.util.List;

public interface HomeService {

    List<Movie> movieList();
    List<Movie> movieList(Integer page, Model model);

}
