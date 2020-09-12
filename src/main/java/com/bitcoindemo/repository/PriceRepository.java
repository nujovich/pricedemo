package com.bitcoindemo.repository;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.bitcoindemo.store.PriceStore;
import com.bitcoindemo.util.UtilDateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceRepository {

    @Autowired
    private UtilDateTimeFormatter utilFormatter;

    public Map<LocalDateTime, Double> savePrice(String price) {
        String dateTime = utilFormatter.convertToString(LocalDateTime.now());
        LocalDateTime dateTimeParsed = utilFormatter.convertToLocalDateTime(dateTime);
        Double p = new Double(price);
        PriceStore priceStore = PriceStore.getInstance();
        Map<LocalDateTime, Double> pricesMap = priceStore.getPricesMap();
        pricesMap.put(dateTimeParsed, p);
        return pricesMap;
    }

    public Map<LocalDateTime, Double> getPrices(LocalDateTime datetime1, LocalDateTime datetime2) {
        PriceStore priceStore = PriceStore.getInstance();
        Map<LocalDateTime, Double> pricesMap = priceStore.getPricesMap();
        Map<LocalDateTime, Double> result = pricesMap.entrySet().stream()
                .filter(d -> d.getKey().isEqual(datetime1) || d.getKey().isEqual(datetime2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return result;

    }

    public Double getMaxPrice() {
        return PriceStore.getInstance().getMaxPrice();
    }

    public Entry<LocalDateTime, Double> getPriceByLocalDateTime(LocalDateTime datetime) {
        PriceStore priceStore = PriceStore.getInstance();
        Map<LocalDateTime, Double> pricesMap = priceStore.getPricesMap();
        Entry<LocalDateTime, Double> result = pricesMap.entrySet().stream()
                .filter(d -> d.getKey().isEqual(datetime))
                .findAny()
                .orElse(null);
        return result;        
    }
    
}