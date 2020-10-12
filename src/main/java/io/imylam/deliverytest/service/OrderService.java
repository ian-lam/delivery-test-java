package io.imylam.deliverytest.service;

import io.imylam.deliverytest.dto.model.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(String[] origin, String[] destination);
    boolean takeOrder(int id);
    List<OrderDto> listOrder(int limit, int page);
}
