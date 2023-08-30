package com.project.MovieMania.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieMania.domain.DTO.MovieDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QryMovieList extends QryResult{
	
	@JsonProperty("data")
	List<MovieDTO> list;
}
