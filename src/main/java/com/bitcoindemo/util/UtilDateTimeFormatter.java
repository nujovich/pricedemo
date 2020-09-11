package com.bitcoindemo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class UtilDateTimeFormatter {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String convertToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public LocalDateTime convertToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }
    
}