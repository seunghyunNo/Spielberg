package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Question;
import com.project.MovieMania.domain.TicketInfo;
import com.project.MovieMania.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
                                                    // 여기서 어떤 도메인을 가져오나?
public interface MyPageRepository extends JpaRepository<User,Long> {

    // findById 기본제공은 알겠는데 퀘스쳔 도메인에서 어떻게 가져오나???
//    Question findById(long id);



        List<TicketInfo> findByFromRowAndPageRowsAndId (int fromRow, int pageRows, long id);

}
