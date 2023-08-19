package com.project.MovieMania.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.MovieMania.domain.type.Gender;
import com.project.MovieMania.domain.type.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "customer")
public class User extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	
	@Transient
	@ToString.Exclude
	@JsonIgnore
	private String re_password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String phoneNum;
	
	@Column(nullable = false)
	private LocalDate birthday;
	
	
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	@ColumnDefault(value = "ACTIVE")
	private UserStatus status;
	
	
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@ToString.Exclude
	@JsonIgnore
	@Builder.Default
	private List<Authority> authorities = new ArrayList<>();
	
	public void addAuthorities(Authority... authorities){
		if (authorities != null) {
			Collections.addAll(this.authorities, authorities);
		}
	}
	
	@OneToMany
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	@Builder.Default
	private List<Question> questions = new ArrayList<>();
	
	public void addQuestions(Question... questions){
		if(questions != null){
			Collections.addAll(this.questions, questions);
		}
	}
	
}
