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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class TicketInfo extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ticketCode;
	
	@ManyToOne
	private ShowInfo showInfo;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private PriceInfo priceInfo;
	
	@OneToMany
	@Builder.Default
	@JoinColumn(name = "ticket_info_id")
	private List<Seat> seats = new ArrayList<>();
	
	public void addSeats(Seat... seats){
		if(seats != null){
			Collections.addAll(this.seats, seats);
		}
	}
	
}
