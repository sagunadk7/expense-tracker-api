package com.sagun.expense_tracker_api.entity;

import com.sagun.expense_tracker_api.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(name = "uk_users_email",columnNames = "email"),
        @UniqueConstraint(name = "uk_users_username",columnNames = "username")}


)
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, name = "username",length = 100,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name ="user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = Set.of(Role.USER);


    private Long income;

    @Column(nullable = false)
    private boolean enabled = true;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

}
