package com.zutode.bookshopclone.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BearerTokenDto {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expiration_time")
    private Date expirationTime;

}
