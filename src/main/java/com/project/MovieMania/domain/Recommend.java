package com.project.MovieMania.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recommend {
	@EmbeddedId
	private RecommendPK recommendPK;
	
	
}
