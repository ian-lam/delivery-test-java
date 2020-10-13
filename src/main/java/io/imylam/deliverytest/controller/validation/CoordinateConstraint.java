package io.imylam.deliverytest.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoordinateValidator.class)
@Documented
public @interface CoordinateConstraint {
    String message() default "invalid coordinate";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
