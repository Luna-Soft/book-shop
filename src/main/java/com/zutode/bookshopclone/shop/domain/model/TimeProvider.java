package com.zutode.bookshopclone.shop.domain.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TimeProvider {

    public LocalDate now(){
        return LocalDate.now();
    }
}
