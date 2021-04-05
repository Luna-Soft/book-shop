package com.zutode.bookshopclone.shop.application.validator;

import com.zutode.bookshopclone.shop.application.exception.InvalidInputParameterException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class SimpleValidator implements RestConstraintValidator {

    @Override
    public void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldErrors().get(0);
            int errorsLeft = bindingResult.getFieldErrors().size() - 1;
            String format = String.format("@%s.%s: %s there %s %d error%s left", error.getObjectName(),
                    error.getField(),
                    error.getDefaultMessage(),
                    (errorsLeft > 1 ? "are" : "is"),
                    errorsLeft,
                    (errorsLeft > 1 ? "s" : ""));
            throw new InvalidInputParameterException(format);

        }

    }
}
