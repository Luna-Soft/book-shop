package com.zutode.bookshopclone.auth.domain.model.entity;

import com.zutode.bookshopclone.shop.domain.model.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NaturalId
    @NotBlank
    private String email;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role")
    private UserRoles userRole;


    public UserAccount(Long id, String username, String password, String email, UserRoles userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }
}
