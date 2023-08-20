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
@Entity
public class User extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 50)
	private String username;
	
	@JsonIgnore
	@Column(nullable = false, length = 50)
	private String password;
	
	@Transient
	@ToString.Exclude
	@JsonIgnore
	private String re_password;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false, length = 20)
	private String phoneNum;
	
	@Column(nullable = false)
	private LocalDate birthday;
	
	
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
	private UserStatus status;
	
	
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Gender gender;
	
	// User:Authority = N:N
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
	
	// User:Question = 1:N
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
	
	@OneToMany
	@JoinColumn(name = "user_id")
	@Builder.Default
	@ToString.Exclude
	private List<Recommend> recommends = new ArrayList<>();
	
}
