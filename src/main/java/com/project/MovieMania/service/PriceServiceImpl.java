package com.project.MovieMania.service;

import com.project.MovieMania.domain.PriceInfo;
import com.project.MovieMania.repository.PriceInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class PriceServiceImpl implements PriceService {

    private PriceInfoRepository priceInfoRepository;

    @Autowired
    public void setPriceInfoRepository(PriceInfoRepository priceInfoRepository) {
        this.priceInfoRepository = priceInfoRepository;
    }


    @Override
    public PriceInfo checkAdultNum() {

        PriceInfo priceInfo = priceInfoRepository.findById(1L).orElse(null);

        return priceInfo;
    }

    @Override
    public PriceInfo checkStudentNum() {

        PriceInfo priceInfo = priceInfoRepository.findById(2L).orElse(null);

        return priceInfo;
    }
}
