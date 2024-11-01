package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user")
public class User extends AbstractNamedEntity {
    @Column(name = "email", unique = true, nullable = false)
    @Email
    @NotEmpty
    @Size(max = 100)
    private String email;

    @Column(name = "firstName", nullable = false)
    @Size(max = 128)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    @Size(max = 128)
    private String lastName;

    @Column(name = "password", nullable = false)
    @Size(max = 128)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;
    @Column(name = "registered", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"},
                                 name = "user_roles_unique")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {

    }
    public User(User u) {
        this(u.getId(), u.getFirstName(),u.getLastName(), u.getEmail(), u.getPassword(), u.isEnabled(), u.getRoles());
    }
    public User(Integer id, String firstName,String lastName, String email, String password,boolean isEnabled, Role role, Role... roles) {
        this(id, firstName,lastName, email, password, true, EnumSet.of(role, roles));
    }
    public User(Integer id, String firstName,String lastName, String email, String password, boolean enabled, Set<Role> roles) {
        super(id, firstName,lastName);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public String toString() {
        return "User (" +
                "id=" + id +
                ", email=" + email +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ')';
    }


}
