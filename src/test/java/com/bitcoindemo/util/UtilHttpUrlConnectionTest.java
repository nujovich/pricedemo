package com.bitcoindemo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UtilHttpUrlConnectionTest {

    @InjectMocks
    private UtilHttpUrlConnection utilHttpUrlConnectionMock;

    private InputStream inputStream;

    private ByteArrayOutputStream outputStream;

    @Mock
    private HttpURLConnection con;

    private String response;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        inputStream = new ByteArrayInputStream("{\"lprice\":\"10806.1\",\"curr1\":\"BTC\",\"curr2\":\"USD\"}".getBytes());
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void test_getPrice() throws IOException {
        doReturn(outputStream).when(con).getOutputStream();
        doReturn(inputStream).when(con).getInputStream();
        when(con.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) { 
            br.lines().forEach(System.out::println);
            response = br.toString();
        }
        utilHttpUrlConnectionMock.getPrice();
        verify(con).getInputStream();
        assertNotNull(response);
        assertEquals(200, HttpURLConnection.HTTP_OK);

    }
}
