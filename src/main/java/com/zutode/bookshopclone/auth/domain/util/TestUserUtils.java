package com.zutode.bookshopclone.auth.domain.util;

import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import com.zutode.bookshopclone.auth.domain.model.entity.UserRoles;
import com.zutode.bookshopclone.auth.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TestUserUtils {

    @Autowired
    private UserRepository userRepository;


    public void addTestUsers() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        List<UserAccount> userAccounts = Stream.of(
                new UserAccount(101L, "zutode", bCryptPasswordEncoder.encode("password"), "zutode@wp.pl", UserRoles.ROLE_MAINTAINER),
                new UserAccount(102L, "user1", bCryptPasswordEncoder.encode("pwd1"), "user1@wp.pl", UserRoles.ROLE_READER),
                new UserAccount(103L, "user2", bCryptPasswordEncoder.encode("pwd2"), "user2@wp.pl", UserRoles.ROLE_READER),
                new UserAccount(104L, "user3", bCryptPasswordEncoder.encode("pwd3"), "user3@wp.pl", UserRoles.ROLE_READER)
        ).collect(Collectors.toList());

        userRepository.saveAll(userAccounts);
    }
}
