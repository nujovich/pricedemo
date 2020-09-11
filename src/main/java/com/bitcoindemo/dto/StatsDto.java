package com.bitcoindemo.dto;

public class StatsDto {

    private double mean;
    private double maxPrice;
    private double percentageDiff;

    public StatsDto(double mean, double maxPrice, double percentageDiff) {
        this.mean = mean;
        this.maxPrice = maxPrice;
        this.percentageDiff = percentageDiff;
    }

    public StatsDto() {
        
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getPercentageDiff() {
        return percentageDiff;
    }

    public void setPercentageDiff(double percentageDiff) {
        this.percentageDiff = percentageDiff;
    }
    
}