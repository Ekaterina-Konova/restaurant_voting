package ru.ekaterinakonova.restaurantvoting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Menu menu;

    @Column(name = "date", columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    public Vote() {

    }

    public Vote(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public Vote(Vote v){
        this(v.id, v.date);
    }

    @Override
    public String toString() {
        return "Vote (" +
                "user=" + user.getName() +
                ", id=" + menu.getId() +
                ", date='" + date +
                ')';
    }
}
