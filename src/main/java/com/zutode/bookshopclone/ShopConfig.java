package com.zutode.bookshopclone;

import com.zutode.bookshopclone.shop.domain.converter.BookIdToBookConverter;
import com.zutode.bookshopclone.auth.domain.converter.UserIdToUserAccountConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class ShopConfig {

    @Bean
    public ModelMapper modelMapper(UserIdToUserAccountConverter userIdToUserAccountConverter,
                                   BookIdToBookConverter bookIdToBookConverter) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(bookIdToBookConverter);
        modelMapper.addConverter(userIdToUserAccountConverter);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    void setUTCTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
