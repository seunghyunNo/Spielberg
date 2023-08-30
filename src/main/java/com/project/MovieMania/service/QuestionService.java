package com.project.MovieMania.service;

import com.project.MovieMania.domain.Question;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

public interface QuestionService {

    List<Question> list();

    List<Question> list(Integer page, Model model);

    int write(Question question);

    @Transactional
    Question detail(long id);

    Question selectById(long id);

    int update(Question question);

    int answer(Question question);

    int delete(long id);

}
