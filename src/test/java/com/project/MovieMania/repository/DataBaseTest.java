package com.project.MovieMania.repository;

import com.project.MovieMania.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataBaseTest {
	
	@Autowired
	private TempRepository tempRepository;
	
	@Test
	void contextLoads() {
		Movie movie = Movie.builder()
				.title("temp")
				.build();
		
		System.out.println(movie);
	}

}