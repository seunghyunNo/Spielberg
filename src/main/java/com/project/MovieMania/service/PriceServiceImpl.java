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
    public PriceInfo findById(Long priceId,Model model) {
        PriceInfo priceInfo = priceInfoRepository.findById(priceId).orElse(null);
        model.addAttribute("priceId",priceInfo.getId());
        return priceInfo;
    }
}
