package com.project.MovieMania.service;

import com.project.MovieMania.domain.ShowInfo;

import java.util.List;

public interface AdminShowInfoService {
	
	List<ShowInfo> list(String status);
	
	int save(ShowInfo showInfo);
	
	ShowInfo detail(long id);
	
	int delete(long id);
	
}
