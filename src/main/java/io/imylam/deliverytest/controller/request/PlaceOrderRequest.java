package io.imylam.deliverytest.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.imylam.deliverytest.controller.validation.CoordinateConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceOrderRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @CoordinateConstraint
    String[] origin;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    @CoordinateConstraint
    String[] destination; 
}
