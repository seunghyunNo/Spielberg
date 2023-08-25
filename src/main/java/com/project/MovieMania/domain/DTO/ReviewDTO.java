package com.project.MovieMania.domain.DTO;

import com.project.MovieMania.domain.Recommend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private String content;
    private int score;
    private UserDTO user;
    private LocalDateTime createdAt;
    private long recommendCount;
    private List<Recommend> recommends;
}
