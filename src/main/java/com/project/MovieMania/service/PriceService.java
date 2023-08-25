package com.project.MovieMania.service;

import com.project.MovieMania.domain.PriceInfo;
import com.project.MovieMania.repository.PriceInfoRepository;
import org.springframework.ui.Model;

public interface PriceService {

    PriceInfo findById(Long priceId,Model model);
}
