package com.project.MovieMania.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QryCinemaList extends QryResult{
	
	@JsonProperty("cinemaList")
	List<Cinema> cinemas;
}
