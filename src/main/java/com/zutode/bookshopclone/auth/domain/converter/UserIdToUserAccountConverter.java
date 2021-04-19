package com.zutode.bookshopclone.auth.domain.converter;

import com.zutode.bookshopclone.auth.application.dto.id.UserIdDto;
import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import com.zutode.bookshopclone.auth.domain.repository.UserRepository;
import com.zutode.bookshopclone.shop.application.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIdToUserAccountConverter extends AbstractConverter<UserIdDto, UserAccount> {
    private final UserRepository userRepository;

    @Override
    protected UserAccount convert(UserIdDto userIdDto) {
        return userRepository.findById(userIdDto.getId()).orElseThrow(()-> new ResourceNotFoundException("No found"));
    }
}
