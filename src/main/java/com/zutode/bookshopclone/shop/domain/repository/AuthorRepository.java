package com.zutode.bookshopclone.shop.domain.repository;

import com.zutode.bookshopclone.shop.domain.model.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    boolean existsByNameAndSurname(String name, String surname);

    Set<Author> findAllByIdIn(Set<Long> ids);
}
