package com.project.MovieMania.repository;

import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
