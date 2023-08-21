package com.project.MovieMania.repository;

import com.project.MovieMania.domain.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
}
