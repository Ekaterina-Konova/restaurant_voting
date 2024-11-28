package ru.ekaterinakonova.restaurantvoting.repository.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.Optional;


public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional(readOnly = true)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Optional<Vote> getForUserAndDate(@Param("userId") int userId, @Param("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @Override
    @Transactional
    Vote save(Vote vote);

     @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.user.id=:userId AND v.menu.id=:menuId")
    Vote get(@Param("id")int id, @Param("userId") int userId, @Param("menuId") int menuId);
}
