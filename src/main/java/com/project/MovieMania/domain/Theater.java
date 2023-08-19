package com.project.MovieMania.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
	uniqueConstraints = @UniqueConstraint(columnNames = {"theaterNum", "cinema"})
)
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private int theaterNum;
	
	@Column(nullable = false)
	private int maxSeatRow;
	
	@Column(nullable = false)
	private int maxSeatColumn;
	
	// Theater:Cinema = M:1
	@ManyToOne
	private Cinema cinema;
	
	// Theater:ShowInfo = 1:M
	@OneToMany
	@JoinColumn(name = "theater_id")
	@ToString.Exclude
	@Builder.Default
	private List<ShowInfo> showInfos = new ArrayList<>();
	
	public void addShowInfos(ShowInfo... showInfos){
		if(showInfos != null){
			Collections.addAll(this.showInfos, showInfos);
		}
	}
	
}
