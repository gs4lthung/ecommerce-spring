package com.gemsi.orderservice.service;

import com.gemsi.orderservice.dto.OrderRequest;

public interface IOrderService {
    void placeOrder(OrderRequest orderRequest);
}
