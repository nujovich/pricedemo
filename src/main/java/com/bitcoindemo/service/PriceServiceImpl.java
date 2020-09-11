package com.bitcoindemo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.bitcoindemo.dto.StatsDto;
import com.bitcoindemo.repository.PriceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceServiceIF {

    private static DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String getPrice() throws IOException {
        String response = priceRepository.getPrice();
        Map<String, String> map = mapper.readValue(response, new TypeReference<Map<String, String>>() {
        });
        String price = map.get("lprice");
        Map<LocalDateTime, Double> priceStore = priceRepository.savePrice(price);
        System.out.println("####################PRICES STORAGE###################");
        priceStore.entrySet().stream().forEach(System.out::println);
        return price;
    }

    @Override
    public StatsDto getStats(LocalDateTime datetime1, LocalDateTime datetime2) {
        Map<LocalDateTime,Double> result = priceRepository.getPrices(datetime1, datetime2);
        Double maxPrice = priceRepository.getMaxPrice();
        System.out.println("Max price: " + maxPrice);
        List<Double> prices = result.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println("Timestamp1: " + datetime1 + ", price: " + prices.get(0));
        System.out.println("Timestamp2: " + datetime2 + ", price: " + prices.get(1));
        Double mean = new BigDecimal((prices.get(0) + prices.get(1))/2).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double diff = maxPrice - mean;
        Double percentageDiff = new BigDecimal(diff/maxPrice*100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        StatsDto statsDto = new StatsDto(mean, maxPrice, percentageDiff);
        return statsDto;
    }

    @Override
    public Double getPriceByLocalDateTime(LocalDateTime dateTime) {
        Entry<LocalDateTime, Double> result = priceRepository.getPriceByLocalDateTime(dateTime);
        return (result != null) ? result.getValue() : 0.0;
    }
    
}