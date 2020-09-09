package com.bitcoindemo.controller;

import java.io.IOException;

import com.bitcoindemo.service.PriceServiceIF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pricedemo/v1")
public class PriceController {

    @Autowired
    private PriceServiceIF priceServiceIF;

    @Scheduled(fixedRate = 10000)
    @GetMapping(value = "/price")
    public String getPrice() throws IOException {
        System.out.println(priceServiceIF.getPrice());
        return priceServiceIF.getPrice();
        
    }

    @GetMapping(value = "/price/metrics")
    public ResponseEntity<String> getMetrics() {
        return null;
        
    }



}