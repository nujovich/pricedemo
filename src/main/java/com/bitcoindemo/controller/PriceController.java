package com.bitcoindemo.controller;

import java.time.LocalDateTime;

import com.bitcoindemo.dto.StatsDto;
import com.bitcoindemo.exception.PriceNotFoundException;
import com.bitcoindemo.service.PriceServiceIF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pricedemo/v1")
public class PriceController {

    @Autowired
    private PriceServiceIF priceServiceIF;

    @GetMapping(value = "/price/{datetime1}/{datetime2}/stats")
    public StatsDto getStats(@PathVariable("datetime1") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime datetime1,
            @PathVariable("datetime2") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime datetime2)
            throws PriceNotFoundException {
        return priceServiceIF.getStats(datetime1, datetime2);
    } 

        
    @GetMapping(value = "/price/{datetime1}")
    public Double getPriceByLocalDateTime(@PathVariable("datetime1") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime datetime1)
            throws PriceNotFoundException {
        return priceServiceIF.getPriceByLocalDateTime(datetime1);

    }

    



}