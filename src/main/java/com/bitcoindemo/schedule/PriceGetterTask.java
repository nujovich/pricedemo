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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class PriceGetterTask {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UtilHttpUrlConnection utilHttpUrlConnection;
    
    @Async("threadPoolTaskScheduler")
    @Scheduled(fixedRate = 10000)
    public String getPrice() throws IOException, InterruptedException {
        String response = utilHttpUrlConnection.getPrice();
        Map<String, String> map = mapper.readValue(response, new TypeReference<Map<String, String>>() {
        });
        String price = map.get("lprice");
        Map<LocalDateTime, Double> priceStore = priceRepository.savePrice(price);
        System.out.println("####################PRICES STORAGE###################" + " Thread: " + Thread.currentThread().getName());
        priceStore.entrySet().stream().forEach(System.out::println);
        return price;
    }
    
}