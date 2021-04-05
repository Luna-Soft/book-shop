package com.zutode.bookshopclone.shop.domain.repository;

import com.zutode.bookshopclone.shop.domain.model.entity.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long> {

    boolean existsByName(String name);

    Set<Genre> findAllByIdIn(Set<Long> ids);

}
