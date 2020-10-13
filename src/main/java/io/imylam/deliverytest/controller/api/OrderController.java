package io.imylam.deliverytest.controller.api;

import io.imylam.deliverytest.controller.request.PlaceOrderRequest;
import io.imylam.deliverytest.controller.request.TakeOrdersRequest;
import io.imylam.deliverytest.dto.model.OrderDto;
import io.imylam.deliverytest.dto.model.TakeOrderDto;
import io.imylam.deliverytest.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public OrderDto postPlaceOrder(@RequestBody @Valid PlaceOrderRequest req) {
        return placeOrder(req);
    }

    @PatchMapping("/orders/{id}")
    public TakeOrderDto patchTakeOrder(
            @PathVariable @Min(1) String id, @RequestBody @Valid TakeOrdersRequest req) {
        return takeOrder(Integer.parseInt(id));
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrdersList(
            @RequestParam(value = "limit", required = true) @Min(1) int limit,
            @RequestParam(value = "page", required = true) @Min(1) int page) throws Exception {
        return listOrders(limit, page);
    }

    private OrderDto placeOrder(PlaceOrderRequest req) {
        return orderService.placeOrder(req.getOrigin(), req.getDestination());
    }

    private TakeOrderDto takeOrder(int orderID) {
        if (!orderService.takeOrder(orderID)) {
            return new TakeOrderDto().setError("Order not exist or taken");
        }
        return new TakeOrderDto().setStatus("SUCCESS");
    }

    private List<OrderDto> listOrders(int limit, int page) {
        return orderService.listOrder(limit, page);
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
//        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
}
