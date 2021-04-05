package com.zutode.bookshopclone.shop.domain.repository;

import com.zutode.bookshopclone.shop.domain.model.entity.PublishingHouse;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PublishingHouseRepository extends PagingAndSortingRepository<PublishingHouse, Long> {

    boolean existsByName(String name);
}
