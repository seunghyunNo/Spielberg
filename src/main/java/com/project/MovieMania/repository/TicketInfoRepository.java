package com.project.MovieMania.repository;

import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
    TicketInfo findByUserAndShowInfo(User user, ShowInfo showInfo);
}
