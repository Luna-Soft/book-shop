package com.zutode.bookshopclone.shop.domain.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@EqualsAndHashCode(of = {"uuid"})
public abstract class BaseEntity {

    @Column(nullable = false, unique = true, updatable = false)
    private final String uuid = UUID.randomUUID().toString();
    @Version
    @Setter
    @Column(nullable = false)
    private Integer version;
    @Column(name = "creation_time", nullable = false, updatable = false)
    private final LocalDateTime creationTime = LocalDateTime.now();
}
