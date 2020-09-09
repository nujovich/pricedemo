package com.bitcoindemo.store;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PriceStore {

    private static PriceStore instance = null;
    private Map<LocalDateTime,Double> pricesMap;

    private PriceStore() {
        pricesMap = new HashMap<>();
    }

    public static PriceStore getInstance() {
        if(instance == null){
            instance = new PriceStore();
        }
        return instance;
    }

    public Map<LocalDateTime, Double> getPricesMap() {
        return pricesMap;
    }

    public void setPricesMap(Map<LocalDateTime, Double> pricesMap) {
        this.pricesMap = pricesMap;
    }

    
    
}