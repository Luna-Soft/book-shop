package com.zutode.bookshopclone.shop.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookRentalReadDto {


    private Long id;
    @NotNull
    @JsonProperty("user_id")
    private Long userId;
    @NotNull
    @JsonProperty("book_id")
    private Long bookId;
    @NotNull
    @JsonProperty("initial_date")
    private LocalDate initialDate;
    @NotNull
    @JsonProperty("expected_return_date")
    private LocalDate expectedReturnDate;
    @JsonProperty("return_date")
    private LocalDate returnDate;
    private BigDecimal charge;
    private int extension;



}
