package com.project.MovieMania.repository;

import com.project.MovieMania.domain.TicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
}
