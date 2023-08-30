package com.project.MovieMania.service;

import com.project.MovieMania.domain.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.List;

public interface HomeService {

    List<Movie> movieList();
    List<Movie> movieList(Integer page, Model model);

    @Transactional
    List<Movie> search(String keyword);
//
//    @Transactional
//    Page<Movie> search(String keyword, Pageable pageable);

}
