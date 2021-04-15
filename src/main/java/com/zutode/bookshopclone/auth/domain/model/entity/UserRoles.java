package com.zutode.bookshopclone.auth.domain.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {

    ROLE_MAINTAINER {
        @Override
        public String getAuthority() {
            return ROLE_MAINTAINER.toString();
        }
    },

    ROLE_READER {
        @Override
        public String getAuthority() {
            return ROLE_READER.toString();
        }
    }
}
