package com.bitcoindemo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import com.bitcoindemo.store.PriceStore;
import com.bitcoindemo.util.UtilDateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PriceRepositoryTest {

    @InjectMocks
    private PriceRepository priceRepositoryMock;

    @Mock
    private UtilDateTimeFormatter utilDateTimeFormatterMock;

    private String date = "2020-09-13 11:22:37";

    private LocalDateTime dateTime = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 01, 22, 37);
    private LocalDateTime dateTime1 = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 01, 32, 37);

    private Map<LocalDateTime,Double> map = new HashMap<>();

    private Map<LocalDateTime,Double> result = new HashMap<>();

    Map.Entry<LocalDateTime, Double> singleEntry =  new AbstractMap.SimpleEntry<>(dateTime, 12080.1);

    private PriceStore priceStore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        priceStore = PriceStore.getInstance();
        map.put(dateTime, 12080.1);
        map.put(dateTime1, 12180.1);
        map.put(dateTime1, 10180.1);
        result.put(dateTime, 12080.1);
        result.put(dateTime1, 10180.1);
        priceStore.setPricesMap(map);
    }

    @Test
    public void test_getMaxPrice() {
        final Double maxPrice = priceRepositoryMock.getMaxPrice();
        assertEquals(12080.1, maxPrice);
    }
    
    @Test
    public void test_savePrice() {
        when(utilDateTimeFormatterMock.convertToString(dateTime)).thenReturn(date);
        when(utilDateTimeFormatterMock.convertToLocalDateTime(date)).thenReturn(dateTime);
        map = priceStore.getPricesMap();
        map.put(dateTime, 11080.1);
        assertNotNull(map.entrySet());
    }

    @Test
    public void test_getPrices() {
        Map<LocalDateTime, Double> output = priceRepositoryMock.getPrices(dateTime, dateTime1);
        assertNotNull(output.entrySet());
        assertEquals(result, output);
    }

    @Test
    public void test_getPriceByLocalDate() {
        Map.Entry<LocalDateTime, Double> output = priceRepositoryMock.getPriceByLocalDateTime(dateTime);
        assertNotNull(output.getKey());
        assertNotNull(output.getValue());
        assertEquals(singleEntry, output);
    }
}