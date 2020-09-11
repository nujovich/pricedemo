package com.bitcoindemo.service;

import java.io.IOException;
import java.time.LocalDateTime;

import com.bitcoindemo.dto.StatsDto;

public interface PriceServiceIF {
    
    public String getPrice() throws IOException;

    public StatsDto getStats(LocalDateTime datetime1, LocalDateTime datetime2);

    public Double getPriceByLocalDateTime(LocalDateTime dateTime);
}