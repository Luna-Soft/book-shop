package com.zutode.bookshopclone.shop.domain.repository;

import com.zutode.bookshopclone.shop.domain.model.entity.BookRental;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRentalRepository extends PagingAndSortingRepository<BookRental, Long> {

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
    BookRental findByBookId(Long bookId);

}
