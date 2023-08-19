package com.project.MovieMania.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Seat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int seatRow;
	
	private int seatColumn;
	
	@ManyToOne
	@ToString.Exclude
	private TicketInfo ticketInfo;
	
}
