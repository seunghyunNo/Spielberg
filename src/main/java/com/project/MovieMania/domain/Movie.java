package com.project.MovieMania.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class Movie extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String movieCode;
	
	@Column(nullable = false)
	private String title;
	
	private String directors;
	
	private String actors;
	
	private Integer showTime;
	
	private LocalDate openDate;
	
	private String genre;
	
	private String rateGrade;
	
	private String img;
	
	@ColumnDefault("0")
	private long audiCnt;
	
	// Movie:ShowInfo = 1:M
	@OneToMany
	@JoinColumn(name = "movie_id")
	@ToString.Exclude
	@Builder.Default
	private List<ShowInfo> showInfos = new ArrayList<>();
	
	public void addShowInfos(ShowInfo... showInfos){
		if(showInfos != null){
			Collections.addAll(this.showInfos, showInfos);
		}
	}
	
	
	
}
