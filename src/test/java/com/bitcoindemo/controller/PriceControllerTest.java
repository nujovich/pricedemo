package com.bitcoindemo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;

import com.bitcoindemo.dto.StatsDto;
import com.bitcoindemo.exception.PriceNotFoundException;
import com.bitcoindemo.service.PriceServiceIF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PriceControllerTest {

    @InjectMocks
    private PriceController priceControllerMock;

    @Mock
    private PriceServiceIF priceServiceIFMock;

    private LocalDateTime dateTime = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 01, 22, 37);
    private LocalDateTime dateTime1 = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 01, 32, 37);

    private StatsDto statsDto = new StatsDto(12000.1, 12000.1, 0.0);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getStats() throws PriceNotFoundException {
        when(priceServiceIFMock.getStats(dateTime, dateTime1)).thenReturn(statsDto);
        StatsDto output = priceControllerMock.getStats(dateTime, dateTime1);
        assertEquals(statsDto, output);
    }

    @Test
    public void testgetPriceByLocalDateTime() throws PriceNotFoundException {
        when(priceServiceIFMock.getPriceByLocalDateTime(dateTime)).thenReturn(12000.1);
        Double price = priceControllerMock.getPriceByLocalDateTime(dateTime);
        assertEquals(12000.1, price);
    }
    
}