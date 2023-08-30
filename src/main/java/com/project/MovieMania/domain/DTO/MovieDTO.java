package com.project.MovieMania.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@Builder
public class MovieDTO {
	private Long id;
	private String movieCode;
	private String title;
	
	// public MovieDTO(Long id, String movieCode, String title) {
	// 	this.id = id;
	// 	this.movieCode = movieCode;
	// 	this.title = title;
	// }
}
