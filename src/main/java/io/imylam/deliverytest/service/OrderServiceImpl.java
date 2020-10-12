package io.imylam.deliverytest.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.internal.StringJoin;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import io.imylam.deliverytest.config.ApiConfig;
import io.imylam.deliverytest.dto.model.OrderDto;
import io.imylam.deliverytest.model.Order;
import io.imylam.deliverytest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ApiConfig apiConfig;

    @Override
    public OrderDto placeOrder(String[] origin, String[] destination) {
        DistanceMatrix dMatrix = getDistanceFromGoogleMapApi(origin, destination);
        if (dMatrix == null || dMatrix.rows[0].elements[0].status != DistanceMatrixElementStatus.OK) {
            return null;
        }
        long distance = dMatrix.rows[0].elements[0].distance.inMeters;

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

    private DistanceMatrix getDistanceFromGoogleMapApi(String[] origin, String[] destination) {

        String[] origins = new String[1];
        String destinations[] = new String[1];
        origins[0] = StringJoin.join(',', origin);
        destinations[0] = StringJoin.join(',', destination);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiConfig.getGoogleKey())
                .build();
        DistanceMatrixApiRequest req =  DistanceMatrixApi.getDistanceMatrix(
                context, origins, destinations);
        try {
            DistanceMatrix distanceMatrix = req.await();
            return distanceMatrix;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
