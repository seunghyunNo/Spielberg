package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByTitleContaining(String keyword, Pageable pageable);

    List<Question> findByUser_id(Long id);

    Page<Question> findByUser(User user, PageRequest id);

}
