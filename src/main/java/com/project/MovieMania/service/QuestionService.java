package com.project.MovieMania.service;

import com.project.MovieMania.domain.Question;
import org.springframework.ui.Model;

import java.util.List;

public interface QuestionService {

    int write(Question question);

    Question detail(long id);

    List<Question> list();

    List<Question> list(Integer page, Model model);

    Question selectById(long id);

    int update(Question question);

    int deleteById(long id);

}
