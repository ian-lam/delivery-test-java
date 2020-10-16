package io.imylam.deliverytest.service;

import io.imylam.deliverytest.dto.model.OrderDto;
import io.imylam.deliverytest.model.Order;
import io.imylam.deliverytest.repository.OrderRepository;
import io.imylam.deliverytest.thirdpartyservice.GoogleMapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    final private OrderRepository orderRepository;
    final private GoogleMapClient googleMapClient;

    @Override
    public OrderDto placeOrder(String[] origin, String[] destination) {
        long distance;
        try {
            distance = googleMapClient.getDistance(origin, destination);
        } catch (Exception e) {
            return null;
        }

        Order order = orderRepository.save(
                new Order().setDistance(distance).setStatus(Order.STATUS_UNASSIGNED)
        );

        return new OrderDto()
                .setId(order.getId())
                .setDistance(order.getDistance())
                .setStatus(order.getStatus());
    }

    @Override
    public boolean takeOrder(int id) {
        int row = orderRepository.updateStatus(id, Order.STATUS_TAKEN, Order.STATUS_UNASSIGNED);
        return row > 0;
    }

    @Override
    public List<OrderDto> listOrder(int limit, int page) {
        List<Order> orders = orderRepository.findByPage(limit, (page-1)*limit);

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(new OrderDto()
                    .setId(order.getId())
                    .setDistance(order.getDistance())
                    .setStatus(order.getStatus())
            );
        }

        return orderDtos;
    }
}
