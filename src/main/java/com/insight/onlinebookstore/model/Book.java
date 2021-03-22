package com.insight.onlinebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Jimy on 22/03/2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String title;
    private String author;
    private String genre;
    private double price;

    @JsonIgnore
    public double getDiscountPrice(Map<String, Double> discountMap) {
        double discountPercent = discountMap.get(getGenre().toLowerCase()) != null ? discountMap.get(getGenre().toLowerCase()) : 0.00;
        return ((100 - discountPercent) / 100) * getPrice();
    }
}
