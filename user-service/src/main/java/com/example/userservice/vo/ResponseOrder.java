package com.example.userservice.vo;

import lombok.Data;

@Data
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Data createdAt;
    private String oderId;
}
