package com.zutode.bookshopclone.shop.domain.repository;

import com.zutode.bookshopclone.shop.domain.model.entity.BookRental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRentalRepository extends PagingAndSortingRepository<BookRental, Long> {

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    BookRental findByBookId(Long bookId);

    Page<BookRental> findBookRentalByUser(String user, Pageable pageable);

}
