package com.project.MovieMania.service;

import com.project.MovieMania.domain.Purchase;

import java.util.Map;

public interface PurchaseService {
    Purchase paymentKakaoPay(String itemName,String totalCnt,String cost);
}
