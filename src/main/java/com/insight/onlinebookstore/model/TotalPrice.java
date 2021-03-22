package com.insight.onlinebookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Jimy on 22/03/2021.
 */
@Data
@AllArgsConstructor
public class TotalPrice {

    private double totalPrice;
    private double totalPriceWithoutGST;

}
