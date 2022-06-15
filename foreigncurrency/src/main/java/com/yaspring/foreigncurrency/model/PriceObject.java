package com.yaspring.foreigncurrency.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceObject {
    String currency;
    Double price;

    public PriceObject(String currency, Double price) {
        this.currency = currency;
        this.price = price;
    }

}
