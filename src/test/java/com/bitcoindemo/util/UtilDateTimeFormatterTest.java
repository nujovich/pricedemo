package com.bitcoindemo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class UtilDateTimeFormatterTest {
    
    @InjectMocks
    private UtilDateTimeFormatter utilDateTimeFormatterMock;

    private LocalDateTime dateTime = LocalDateTime.of(2020, Month.SEPTEMBER, 13, 11, 22, 37);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_convertToString() {
        assertEquals("2020-09-13 11:22:37", utilDateTimeFormatterMock.convertToString(dateTime));
    }

    @Test
    public void test_convertToLocalDateTime() {
        assertEquals(dateTime, utilDateTimeFormatterMock.convertToLocalDateTime("2020-09-13 11:22:37"));
    }

}