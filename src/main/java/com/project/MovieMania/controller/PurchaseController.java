package com.project.MovieMania.controller;

import com.project.MovieMania.domain.Purchase;
import com.project.MovieMania.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/payment")
    public @ResponseBody Purchase payment(@RequestParam String itemName,@RequestParam String totalCnt,@RequestParam String cost
            ,@RequestParam String showInfoId)
    {
        Long id = Long.parseLong(showInfoId);
        Purchase response = purchaseService.paymentKakaoPay(itemName,totalCnt,cost,id);

        System.out.println("결제고유번호: "+ response.getTid());
        System.out.println("결제요청 URL"+response.getNext_redirect_pc_url());

        return response;
    }

    @GetMapping("/success/{showInfoId}")
    public String success(@PathVariable Long showInfoId,Model model)
    {
        model.addAttribute("showInfoId",showInfoId);
        return "/purchase/success";
    }

    @GetMapping("/cancle")
    public String cancle()
    {
        return "/purchase/cancle";
    }

    @GetMapping("/fail")
    public String fail()
    {
        return "/purchase/fail";
    }
}
