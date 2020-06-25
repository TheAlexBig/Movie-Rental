package com.brick.buster.main.response;

import com.brick.buster.main.response.interfaces.Response;

import java.util.List;

public class ErrorResponse extends Response {
    private List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
