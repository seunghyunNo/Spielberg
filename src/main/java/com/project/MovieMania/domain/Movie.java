package com.project.MovieMania.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

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
@DynamicInsert
@DynamicUpdate
@Entity
public class Movie extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, length = 30)
	private String movieCode;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(length = 50)
	private String directors;
	
	private String actors;
	
	private Integer showTime;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private LocalDate openDate;
	
	@Column(length = 100)
	private String genre;
	
	@Column(length = 100)
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
