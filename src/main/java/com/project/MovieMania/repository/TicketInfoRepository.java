package com.project.MovieMania.repository;

import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.Movie;
import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.domain.User;
import com.project.MovieMania.domain.type.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
    TicketInfo findByUserAndShowInfo(User user, ShowInfo showInfo);

    List<TicketInfo> findByShowInfoIn(List<ShowInfo> showInfoList);

    List<TicketInfo> findByShowInfoInAndUser_Gender(List<ShowInfo> upcomingMovie, Gender gender);

    List<TicketInfo> findByShowInfoInAndUser_BirthdayBetween(List<ShowInfo> upcomingMovie, LocalDate localDate, LocalDate localDate1);
}
