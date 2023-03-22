package com.example.orderservice.vo;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}
