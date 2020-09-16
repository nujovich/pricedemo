package com.bitcoindemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitcoindemo.dto.StatsDto;
import com.bitcoindemo.exception.PriceNotFoundException;
import com.bitcoindemo.repository.PriceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PriceServiceImplTest {

    @InjectMocks
    private PriceServiceImpl priceServiceImplMock;

    @Mock
    private PriceRepository priceRepositoryMock;

    private Map<LocalDateTime, Double> map = new HashMap<>();
    private Map<LocalDateTime, Double> map2 = new HashMap<>();


    private LocalDateTime dateTime = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 01, 22, 37);
    private LocalDateTime dateTime1 = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 01, 32, 37);

    Map.Entry<LocalDateTime, Double> result =  new AbstractMap.SimpleEntry<>(dateTime1, 12180.1);

    private List<Double> prices = new ArrayList<>();

    private List<Double> prices1 = new ArrayList<>();


    private double mean, percentageDiff, priceMax;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        map.put(dateTime, 12080.1);
        map.put(dateTime1, 12180.1);
        map2.put(dateTime1, 12180.1);
        prices.add(12080.1);
        prices.add(12180.1);
        prices1.add(12180.1);
        mean = (12080.1 + 12180.1) / 2;
        priceMax = 12180.1;
        percentageDiff = new BigDecimal((priceMax - mean) / priceMax * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Test
    public void test_getStats() throws PriceNotFoundException {
        when(priceRepositoryMock.getPrices(dateTime, dateTime1)).thenReturn(map);
        when(priceRepositoryMock.getMaxPrice()).thenReturn(12180.1);
        StatsDto output = priceServiceImplMock.getStats(dateTime, dateTime1);
        assertEquals(mean, output.getMean());
        assertEquals(priceMax, output.getMaxPrice());
        assertEquals(percentageDiff, output.getPercentageDiff());
        assertFalse(prices.isEmpty());
    }

    @Test
    public void test_getStats_shouldThrowPriceNotFoundException() {
        when(priceRepositoryMock.getPrices(dateTime, dateTime1)).thenReturn(map2);
        when(priceRepositoryMock.getMaxPrice()).thenReturn(12180.1);
        PriceNotFoundException ex = assertThrows(PriceNotFoundException.class, () -> {
            priceServiceImplMock.getStats(dateTime, dateTime1);
        });
        assertEquals(1, prices1.size());
        assertNotNull(ex.getMessage());
    }

    @Test
    public void test_getPriceByLocalDateTime() throws PriceNotFoundException {
        when(priceRepositoryMock.getPriceByLocalDateTime(dateTime1)).thenReturn(result);
        Double output = priceServiceImplMock.getPriceByLocalDateTime(dateTime1);
        assertEquals(result.getValue(), output);
    }

    @Test
    public void test_getPriceByLocalDateTime_shouldThrowPriceNotFoundException() {
        when(priceRepositoryMock.getPriceByLocalDateTime(dateTime)).thenReturn(null);
        PriceNotFoundException ex = assertThrows(PriceNotFoundException.class, () -> {
            priceServiceImplMock.getPriceByLocalDateTime(dateTime);
        });
        assertNotNull(ex.getMessage());
    }
    
}