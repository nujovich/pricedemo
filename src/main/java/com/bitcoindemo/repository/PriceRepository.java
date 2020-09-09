package com.bitcoindemo.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.bitcoindemo.store.PriceStore;

import org.springframework.stereotype.Component;

@Component
public class PriceRepository {

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
        if(responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
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
        LocalDateTime dateTime = LocalDateTime.now();
        Double p = new Double(price);
        PriceStore priceStore = PriceStore.getInstance();
        Map<LocalDateTime,Double> pricesMap = priceStore.getPricesMap();
        pricesMap.put(dateTime, p);
        return pricesMap;
        
    }
    
}