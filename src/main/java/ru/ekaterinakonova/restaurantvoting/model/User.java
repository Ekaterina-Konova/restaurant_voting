package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = "password")
public class User extends AbstractPersistable<Integer> {
    @Column(name = "email", unique = true, nullable = false)
    @Email
    @NotEmpty
    @Size(max = 100)
    private String email;

    @Column(name = "firstName")
    @Size(max = 128)
    private String firstName;

    @Column(name = "lastName")
    @Size(max = 128)
    private String lastName;

    @Column(name = "password")
    @Size(max = 128)
    private String password;
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"},
                                 name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
