package com.zutode.bookshopclone.shop.domain.model.entity;

import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Getter
@Setter
public class BookRental extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @NotNull
    private LocalDate initialDate;
    @NotNull
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private BigDecimal charge;
    private int extension;

}
