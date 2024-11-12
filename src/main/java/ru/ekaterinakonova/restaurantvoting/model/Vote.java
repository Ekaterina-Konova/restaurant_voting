package ru.ekaterinakonova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "unique_vote")})
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    public Vote() {

    }

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.date = date;
    }

    public Vote(Restaurant restaurant, User user, LocalDate date) {
        this(null, restaurant, user, date);
    }

    @Override
    public String toString() {
        return "Vote (" +
                "user=" + user.getName() +
                ", id=" + restaurant.getId() +
                ", date='" + date +
                ')';
    }
}
