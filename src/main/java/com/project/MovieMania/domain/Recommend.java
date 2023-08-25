package com.project.MovieMania.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recommend {
	@EmbeddedId
	private RecommendPK recommendPK;

	@ManyToOne
	@MapsId("user_id")
	@JsonIgnore
	private User user;

	@ManyToOne
	@MapsId("review_id")
	@JsonIgnore
	private Review review;
	
	
}
