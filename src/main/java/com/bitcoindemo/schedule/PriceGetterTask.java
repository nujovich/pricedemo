package com.bitcoindemo.schedule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import com.bitcoindemo.repository.PriceRepository;
import com.bitcoindemo.util.UtilHttpUrlConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceGetterTask {


    @Autowired
    private UtilHttpUrlConnection utilHttpUrlConnection;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PriceRepository priceRepository;
    
    @Scheduled(fixedRate = 10000)
    @Async("taskExecutor")
    public void getPrice() throws IOException, InterruptedException {
        String response = utilHttpUrlConnection.getPrice();
        Map<String, String> map = mapper.readValue(response, new TypeReference<Map<String, String>>() {
        });
        String price = map.get("lprice");
        Map<LocalDateTime, Double> priceStore = priceRepository.savePrice(price);
        System.out.println("####################PRICES STORAGE###################" + " Thread: " + Thread.currentThread().getName());
        priceStore.entrySet().stream().forEach(System.out::println);
    }
    
}