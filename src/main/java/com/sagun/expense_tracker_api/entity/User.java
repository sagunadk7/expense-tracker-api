package com.sagun.expense_tracker_api.entity;

import com.sagun.expense_tracker_api.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        name = "users"
)
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, name = "full_name",length = 100)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private boolean enabled = true;


}
