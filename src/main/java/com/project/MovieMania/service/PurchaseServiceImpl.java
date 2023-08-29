package com.project.MovieMania.service;

import com.project.MovieMania.domain.Purchase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    @Override
    public Purchase paymentKakaoPay(String itemName,String totalCnt,String cost) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","KakaoAK a6fd627cc7f7209f3f038eb3d0e2860c");
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> paymentData = new LinkedMultiValueMap<>();

        paymentData.add("cid","TC0ONETIME");
        paymentData.add("partner_order_id","Kakao20230829");
        paymentData.add("partner_user_id","KakaoPay");
        paymentData.add("item_name",itemName);
        paymentData.add("quantity",totalCnt);
        paymentData.add("total_amount",cost);
        paymentData.add("tax_free_amount","0");
        paymentData.add("approval_url","http://localhost:8093/purchase/success");
        paymentData.add("cancel_url","http://localhost:8093/purchase/cancel");
        paymentData.add("fail_url","http://localhost:8093/purchase/fail");

        HttpEntity<Map> request = new HttpEntity<Map>(paymentData,headers);

        RestTemplate template = new RestTemplate();

        String url = "https://kapi.kakao.com/v1/payment/ready";

        Purchase purchase = template.postForObject(url,request,Purchase.class);

        return purchase;
    }
}
