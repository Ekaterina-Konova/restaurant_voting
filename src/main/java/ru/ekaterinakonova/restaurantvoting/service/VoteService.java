package ru.ekaterinakonova.restaurantvoting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ekaterinakonova.restaurantvoting.model.Vote;
import ru.ekaterinakonova.restaurantvoting.repository.vote.VoteRepositoryImpl;

import static ru.ekaterinakonova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private final VoteRepositoryImpl repository;

    @Autowired
    public VoteService(VoteRepositoryImpl repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "dish must not be null");
        return repository.save(vote, userId);
    }

    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "dish must not be null");
        checkNotFoundWithId(repository.save(vote, userId), vote.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

}
