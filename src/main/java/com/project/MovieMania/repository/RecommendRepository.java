package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Recommend;
import com.project.MovieMania.domain.Review;
import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

    Recommend findByUserAndReview(User user, Review review);

    void deleteByUserAndReview(User user, Review review);

    List<Recommend> findByReview(Review review);
}
