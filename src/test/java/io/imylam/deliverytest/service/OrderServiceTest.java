package io.imylam.deliverytest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.imylam.deliverytest.config.ApiConfig;
import io.imylam.deliverytest.dto.model.OrderDto;
import io.imylam.deliverytest.model.Order;
import io.imylam.deliverytest.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OrderServiceTest {
    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private ApiConfig apiConfig = Mockito.mock(ApiConfig.class);
    private OrderServiceImpl orderService;

    @BeforeEach
    void initUseCase() {
        orderService = new OrderServiceImpl(orderRepository, apiConfig);
    }

    @Test
    void savedUserHasRegistrationDate() {
        Order order = new Order().setId(1).setDistance(100).setStatus(Order.STATUS_UNASSIGNED);
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());
        when(apiConfig.getGoogleKey()).thenReturn("test");

        String[] origin = {"0.00", "0.00"};
        String[] destination = {"0.00", "0.00"};
        OrderDto orderDto = orderService.placeOrder(origin, destination);
        assertThat(orderDto.getId()).isNotNull();
    }
}
