package com.yaspring.foreigncurrency.controller;

import com.yaspring.foreigncurrency.model.PriceObject;
import com.yaspring.foreigncurrency.model.Product;
import com.yaspring.foreigncurrency.service.FCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CurrencyController {

    @Autowired
    FCService fcService;
/*
    @GetMapping("/product")
    public String getCurrency() {
        var sek = fcService.changeCurrency();
        return String.valueOf(sek);
    }
 */
    @PostMapping("/price")
    // this method will convert the price of single product into SEK by default
    public Double convertPriceOfSingleProduct(@RequestBody Product product) {
        var cost = product.getUnitPrice();
        System.out.println("unit price in double as input = "+ cost);
        var changedPrice = fcService.changeCurrency_post(cost);
        return changedPrice;
    }

    @PostMapping("/{price}/{desiredCurrency}")
    public Double convertPriceInRequiredCurrency(@PathVariable Double price, @PathVariable String desiredCurrency) {
        double a = fcService.changeCostInRequiredCurrency(price,desiredCurrency);
        return a;
    }

    @PostMapping("/convert")
    public Double convertPrice(@RequestBody PriceObject object) {
        double a = fcService.changeCostInRequiredCurrency(object.getPrice(), object.getCurrency());
        return a;
    }

}
