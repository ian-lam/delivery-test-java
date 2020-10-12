package io.imylam.deliverytest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity(name = "orders")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    public static final String STATUS_TAKEN = "TAKEN";
    public static final String STATUS_UNASSIGNED = "UNASSIGNED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long distance;
    private String status;
}