package com.zutode.bookshopclone.shop.domain.repository;

import com.zutode.bookshopclone.shop.domain.model.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    boolean existsByISBN(String ISBN);
}
