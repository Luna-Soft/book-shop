package com.zutode.bookshopclone.auth.domain.repository;

import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserAccount, Long> {


    Optional<UserAccount> findByUsername(String username);
}
