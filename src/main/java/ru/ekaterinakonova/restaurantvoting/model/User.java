package ru.ekaterinakonova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Setter
@Getter
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity {
    @Column(name = "email", unique = true, nullable = false)
    @Email
    @NotBlank
    @SafeHtml
    @Size(max = 100)
    private String email;

    @Column(name = "firstname", nullable = false)
    @Size(max = 128)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @Size(max = 128)
    private String lastName;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @Size(max = 128)
    @SafeHtml
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

    @Column(name = "registered", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"},
                    name = "user_role_unique_idx ")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date DESC")
    private List<Vote> votes = Collections.emptyList();

    public User() {

    }

    public User(User u) {
        this(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), u.isEnabled(), u.getRegistered(), u.getRoles());
    }

    public User(Integer id, String firstName, String lastName, String email, String password, Role role, Role... roles) {
        this(id, firstName, lastName, email, password, true, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String firstName, String lastName, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, firstName, lastName);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public void setVotes(List<Vote> votes) {
        this.votes.addAll(votes);
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
