package com.project.MovieMania.service;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    
    public QuestionServiceImpl(){
        System.out.println("QuestionService() 생성");
    }

    @Override
    public int write(Question question) {
        return 0;
    }

    @Override
    public Question detail(long id) {
        return null;
    }

    @Override
    public List<Question> list() {
        return null;
    }

    @Override
    public List<Question> list(Integer page, Model model) {
        return null;
    }

    @Override
    public Question selectById(long id) {
        return null;
    }

    @Override
    public int update(Question question) {
        return 0;
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }
}
