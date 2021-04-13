/*
com.package com.zutode.bookshopclone.shop.domain.model.entity;

import com.zutode.bookshopclone.shop.domain.model.TimeProvider;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Embeddable
public class ReturnModel {

    private final TimeProvider timeProvider;
    @Value("${bookshop.lending.duration}")
    int rentalDuration;
    @Value("${bookshop.lending.charge}")
    BigDecimal chargePerDay;

    @NotNull
    private LocalDate initialDate;
    @NotNull
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private BigDecimal charge;
    private int extension;

    public void extend() {
        if (extension > 1) {
            throw new RuntimeException();
        }
        extension++;
    }

    public void returnBook() {
        LocalDate current = timeProvider.now();
        long exceededTime = DAYS.between(current, expectedReturnDate);
        if (exceededTime > 0) {
            charge = chargePerDay.multiply(new BigDecimal(String.valueOf(exceededTime)));
        }
        returnDate = current;
    }

    public void extend(){
        if (expectedReturnDate.isBefore(timeProvider.now())) {
          if()
        }
        if (bookRental.getExtension() >= 2) {
            throw new IllegalStateException("You have just extended book twice!");
        }
    }

}


 */
