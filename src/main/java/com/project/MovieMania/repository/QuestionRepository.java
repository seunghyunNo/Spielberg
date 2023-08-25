package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByTitleContaining(String keyword, Pageable pageable);

}
