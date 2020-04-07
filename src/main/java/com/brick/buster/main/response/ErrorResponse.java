package com.brick.buster.main.response;

import com.brick.buster.main.response.interfaces.Response;

import java.util.List;

public class ErrorResponse extends Response {
    private List<Object> errors;

    public ErrorResponse(String message, List<String> errors) {
        super(message);
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
}
