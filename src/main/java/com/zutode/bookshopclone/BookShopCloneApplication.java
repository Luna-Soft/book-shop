package com.zutode.bookshopclone;

import com.zutode.bookshopclone.auth.domain.util.TestUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class BookShopCloneApplication {

    private final TestUserUtils testUserUtils;

    public static void main(String[] args) {
        SpringApplication.run(BookShopCloneApplication.class, args);
    }

    @PostConstruct
    public void init(){
        testUserUtils.addTestUsers();
    }

}