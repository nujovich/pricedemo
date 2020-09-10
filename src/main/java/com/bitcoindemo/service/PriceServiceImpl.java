package com.bitcoindemo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import com.bitcoindemo.repository.PriceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceServiceIF {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String getPrice() throws IOException {
        String response = priceRepository.getPrice();
        Map<String,String> map = mapper.readValue(response, new TypeReference<Map<String, String>>() {});
        String price = map.get("lprice");
        Map<LocalDateTime, Double> priceStore = priceRepository.savePrice(price);
        System.out.println("####################PRICES STORAGE###################");
        priceStore.entrySet().stream().forEach(System.out::println);
        return price;
    }
    
}