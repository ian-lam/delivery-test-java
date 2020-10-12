package io.imylam.deliverytest.dto.mapper;

import io.imylam.deliverytest.dto.model.OrderDto;
import io.imylam.deliverytest.model.Order;

public class OrderMapper {
    public static OrderDto toOrderDto(Order order) {
        return new OrderDto()
            .setId(order.getId())
            .setDistance(order.getDistance())
            .setStatus(order.getStatus());
    }
}
