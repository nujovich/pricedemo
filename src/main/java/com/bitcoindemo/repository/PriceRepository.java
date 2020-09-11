package com.bitcoindemo.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
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

    private static final String GET_URL = "https://cex.io/api/last_price/BTC/USD";

    public String getPrice() throws IOException {
        StringBuffer response;
        String inputLine = "";
        URL url = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "PostmanRuntime/7.26.5");
        con.setRequestProperty("Accept", "*/*");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            inputLine = (Objects.nonNull(response)) ? response.toString() : "Error";
            in.close();
        }
        return inputLine;

    }

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