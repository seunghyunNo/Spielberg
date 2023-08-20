package com.project.MovieMania.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RecommendPK implements Serializable {
	private Long user_id;
	private Long review_id;
}
