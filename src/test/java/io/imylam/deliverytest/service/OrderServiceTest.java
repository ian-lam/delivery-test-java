package io.imylam.deliverytest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.imylam.deliverytest.dto.model.OrderDto;
import io.imylam.deliverytest.model.Order;
import io.imylam.deliverytest.repository.OrderRepository;
import io.imylam.deliverytest.thirdpartyservice.GoogleMapClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OrderServiceTest {
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final GoogleMapClient googleMapClient = Mockito.mock(GoogleMapClient.class);
    private OrderServiceImpl orderService;

    @BeforeEach
    void initUseCase() {
        orderService = new OrderServiceImpl(orderRepository, googleMapClient);
    }

    @Test
    void placeOrderSuccessfully() {
        String[] origin = {"0.00", "0.00"};
        String[] destination = {"0.00", "0.00"};

        long distance = 100L;
        try {
            when(googleMapClient.getDistance(origin, destination)).thenReturn(distance);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }

        Order order = new Order().setId(1).setDistance(distance).setStatus(Order.STATUS_UNASSIGNED);
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        OrderDto orderDto = orderService.placeOrder(origin, destination);
        assertThat(orderDto.getId()).isNotNull();
        assertThat(orderDto.getDistance()).isEqualTo(distance);
        assertThat(orderDto.getStatus()).isEqualTo(Order.STATUS_UNASSIGNED);
    }
}
