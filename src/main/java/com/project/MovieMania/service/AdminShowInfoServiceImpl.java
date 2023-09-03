package com.project.MovieMania.service;

import com.project.MovieMania.domain.ShowInfo;
import com.project.MovieMania.domain.type.ShowInfoStatus;
import com.project.MovieMania.repository.ShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminShowInfoServiceImpl implements AdminShowInfoService{
	
	private ShowInfoRepository showInfoRepository;
	
	@Autowired
	public void setShowInfoRepository(ShowInfoRepository showInfoRepository) {
		this.showInfoRepository = showInfoRepository;
	}
	
	@Override
	public List<ShowInfo> list(String status) {
		if(status.equals("ALL")){
			return showInfoRepository.findAll();
		}else{
			ShowInfoStatus showInfoStatus = ShowInfoStatus.valueOf(status);
			return showInfoRepository.findByStatusOrderByShowDateTimeDesc(showInfoStatus);
		}
	}
	
	@Override
	public int register(ShowInfo showInfo) {
		try{
			showInfoRepository.saveAndFlush(showInfo);
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
			return 0;
		}
		return 1;
	}
	
	@Override
	public int update(ShowInfo showInfo) {
		try{
			ShowInfo show = showInfoRepository.findById(showInfo.getId()).orElseThrow(() -> new RuntimeException());
			show.setStatus(showInfo.getStatus());
			show.setShowDateTime(showInfo.getShowDateTime());
			show.setMovie(show.getMovie());
			show.setTheater(show.getTheater());
			showInfoRepository.saveAndFlush(show);
		}catch (RuntimeException e){
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			System.out.println(showInfo);
			return 0;
		}
		
		return 1;
	}
	
	@Override
	public ShowInfo detail(long id) throws RuntimeException{
		ShowInfo showInfo = showInfoRepository.findById(id).orElseThrow(() -> new RuntimeException());
		return showInfo;
	}
	
	@Override
	public int delete(long id) {
		try{
			ShowInfo showInfo = showInfoRepository.findById(id).orElseThrow(() -> new RuntimeException());
			showInfoRepository.delete(showInfo);
		}catch (RuntimeException e){
			return 0;
		}
		return 1;
	}
}
