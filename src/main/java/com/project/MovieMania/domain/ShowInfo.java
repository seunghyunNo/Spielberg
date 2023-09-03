package com.project.MovieMania.domain;

import com.project.MovieMania.domain.type.ShowInfoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
	@Column(nullable = false, length = 10)
	private ShowInfoStatus status;
	
	@Column(nullable = false)
	@DateTimeFormat(pattern = "MM/dd/yyyy'T'HH:mm")
	private LocalDateTime showDateTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Movie movie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Theater theater;
	
}
