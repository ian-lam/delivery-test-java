package io.imylam.deliverytest.controller.validation;

public class ValidationErrorResponse {
    private final String error;

    public ValidationErrorResponse(String errorMsg) {
        this.error = errorMsg;
    }

    public String getError() {
        return error;
    }
}
