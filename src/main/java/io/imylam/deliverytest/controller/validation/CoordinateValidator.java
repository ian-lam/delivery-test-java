package io.imylam.deliverytest.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.Float.isFinite;

public class CoordinateValidator implements ConstraintValidator<CoordinateConstraint, String[]> {

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        try {
            if (value.length != 2) {
                return false;
            }

            float latitude = Float.parseFloat(value[0]);
            float longitude = Float.parseFloat(value[1]);
            if (!(isFinite(latitude) && Math.abs(latitude) <= 90)) {
                return false;
            }
            return isFinite(longitude) && Math.abs(longitude) <= 180;
        } catch (Exception e) {
            return false;
        }
    }
}
