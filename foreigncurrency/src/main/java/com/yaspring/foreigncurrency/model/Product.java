package com.yaspring.foreigncurrency.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {

        private int productID;
        private String productName;
        private int supplierID;
        private int categoryID;
        private String quantityPerunit;
        private double unitPrice;
        private int unitsInStock;
        private int unitsOnorder;
        private int Reorderlevel;
        private boolean isDiscontinued;
        private List recomendations;

}
