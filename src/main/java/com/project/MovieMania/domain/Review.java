package com.project.MovieMania.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Review extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
	private int score;
	
	@ManyToOne
	private Movie movie;
	
	// 리뷰 작성자
	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
	private List<Report> reports;
	
	// Review:Recommend = 1:N
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "review_id")
	@Builder.Default
	private List<Recommend> recommends = new ArrayList<>();
	
	public void addRecommends(Recommend... recommends){
		if(recommends != null){
			Collections.addAll(this.recommends, recommends);
		}
	}
	
}
