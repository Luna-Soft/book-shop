package com.zutode.bookshopclone.auth.domain.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class BearerToken {

    private final String accessToken;
    private final Date expirationTime;
}
