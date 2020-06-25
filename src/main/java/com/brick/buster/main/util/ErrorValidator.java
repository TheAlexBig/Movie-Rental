package com.brick.buster.main.util;

import com.brick.buster.main.response.ErrorResponse;
import com.brick.buster.main.response.interfaces.Response;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ErrorValidator {
    public Optional<Response> verifyBindingResult(BindingResult bindingResult)   {
        if (bindingResult.hasErrors()) {
            List<String> problems  = bindingResult.getFieldErrors().stream().map(e ->
                    "->" + e.getField() + " : " + e.getDefaultMessage())
                    .collect(Collectors.toList());
            return Optional.of(new ErrorResponse("Some fields contain errors", problems));
        }
        return Optional.empty();
    }
}

