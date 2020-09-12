package com.bitcoindemo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.bitcoindemo.dto.StatsDto;
import com.bitcoindemo.exception.PriceNotFoundException;
import com.bitcoindemo.repository.PriceRepository;
import com.bitcoindemo.util.UtilHttpUrlConnection;
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

    @Autowired
    private UtilHttpUrlConnection utilHttpUrlConnection;

    @Override
    public String getPrice() throws IOException {
        String response = utilHttpUrlConnection.getPrice();
        Map<String, String> map = mapper.readValue(response, new TypeReference<Map<String, String>>() {
        });
        String price = map.get("lprice");
        Map<LocalDateTime, Double> priceStore = priceRepository.savePrice(price);
        System.out.println("####################PRICES STORAGE###################");
        priceStore.entrySet().stream().forEach(System.out::println);
        return price;
    }

    @Override
    public StatsDto getStats(LocalDateTime datetime1, LocalDateTime datetime2) throws PriceNotFoundException {
        Map<LocalDateTime,Double> result = priceRepository.getPrices(datetime1, datetime2);
        Double maxPrice = priceRepository.getMaxPrice();
        List<Double> prices = result.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        if(prices != null && prices.size() == 2) {
            Double mean = new BigDecimal((prices.get(0) + prices.get(1))/2).setScale(2, RoundingMode.HALF_UP).doubleValue();
            Double diff = maxPrice - mean;
            Double percentageDiff = new BigDecimal(diff/maxPrice*100).setScale(2, RoundingMode.HALF_UP).doubleValue();
            StatsDto statsDto = new StatsDto(mean, maxPrice, percentageDiff);
            return statsDto;
        } else {
            throw new PriceNotFoundException("There's not too much information for either of the selected datetimes. Please choose different datetimes");
        }
    }

    @Override
    public Double getPriceByLocalDateTime(LocalDateTime dateTime) throws PriceNotFoundException {
        Entry<LocalDateTime, Double> result = priceRepository.getPriceByLocalDateTime(dateTime);
        Double price =  (result != null) ? result.getValue() : 0.0;
        if(price.equals(0.0)) {
            throw new PriceNotFoundException("There's no price for the selected datetime.");
        } else {
            return price;
        }

    }
    
}