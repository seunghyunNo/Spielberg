package com.project.MovieMania.service;

import com.project.MovieMania.domain.ShowInfo;

import java.util.List;

public interface AdminShowInfoService {
	
	List<ShowInfo> list(String status);
	
	int register(ShowInfo showInfo);
	
	int update(ShowInfo showInfo);
	
	ShowInfo detail(long id);
	
	int delete(long id);
	
}
