package io.imylam.deliverytest.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    String[] origin;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    String[] destination; 
}
