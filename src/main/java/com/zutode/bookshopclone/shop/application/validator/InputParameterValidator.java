package com.zutode.bookshopclone.shop.application.validator;


import com.zutode.bookshopclone.shop.application.exception.InvalidInputParameterException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;


@Service
public class InputParameterValidator implements RestConstraintValidator {

    @Override
    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> "@" + error.getObjectName() + "." + error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new InvalidInputParameterException(errors);

        }
    }
}
