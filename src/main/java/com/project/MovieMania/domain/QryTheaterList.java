package com.project.MovieMania.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.MovieMania.domain.DTO.TheaterDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QryTheaterList extends QryResult{
	
	@JsonProperty("theaterList")
	List<TheaterDTO> theaterDTOs;
}
