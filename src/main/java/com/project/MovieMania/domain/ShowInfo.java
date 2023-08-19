package com.project.MovieMania.domain;

import com.project.MovieMania.domain.type.ShowInfoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShowInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ShowInfoStatus status;
	
	@Column(nullable = false)
	private LocalDateTime showDateTime;
	
	@ManyToOne
	private Movie movie;
	
	@ManyToOne
	private Theater theater;
	
}
