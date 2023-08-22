package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    // 특정 이름의 권한 읽어오기
    Authority findByName(String name);

    List<Authority> findAuthorityById(Long id);

}
