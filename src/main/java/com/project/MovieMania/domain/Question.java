package com.project.MovieMania.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Question extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(columnDefinition = "LONGTEXT", nullable = false)
	private String content;
	
	@Column(columnDefinition = "LONGTEXT")
	private String answer;
	
	@ManyToOne
	private User user;
	
}
