package com.project.MovieMania.domain;

import com.project.MovieMania.domain.type.ReportType;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class Report extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private ReportType type;
	
	@ManyToOne
	private User user; // 신고자
	
	@ManyToOne
	private Review review;
	
}
