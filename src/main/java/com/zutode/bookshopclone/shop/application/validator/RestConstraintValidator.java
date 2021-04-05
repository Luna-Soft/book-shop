package com.zutode.bookshopclone.shop.application.validator;

import org.springframework.validation.BindingResult;

public interface RestConstraintValidator {

    void validate(BindingResult bindingResult);
}
