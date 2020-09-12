package com.bitcoindemo.service;

import java.io.IOException;
import java.time.LocalDateTime;

import com.bitcoindemo.dto.StatsDto;
import com.bitcoindemo.exception.PriceNotFoundException;

public interface PriceServiceIF {
    
    public String getPrice() throws IOException;

    public StatsDto getStats(LocalDateTime datetime1, LocalDateTime datetime2) throws PriceNotFoundException;

    public Double getPriceByLocalDateTime(LocalDateTime dateTime)throws PriceNotFoundException;
}