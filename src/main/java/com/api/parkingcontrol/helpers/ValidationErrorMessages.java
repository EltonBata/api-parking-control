package com.api.parkingcontrol.helpers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
public class ValidationErrorMessages {

    private List<String> errors = new ArrayList<>();

    public List<String> errorMessages(BindingResult result) {

        for (FieldError field : result.getFieldErrors()) {
            this.errors.add(field.getDefaultMessage());
        }
        return this.errors;

    }

}
