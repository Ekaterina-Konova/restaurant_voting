package ru.ekaterinakonova.restaurantvoting.repository.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ekaterinakonova.restaurantvoting.model.Vote;
import ru.ekaterinakonova.restaurantvoting.repository.UserRepository;

@Repository
public class VoteRepositoryImpl {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(userRepository.getOne(userId));
        return voteRepository.save(vote);
    }

    public Vote get(int id, int userId) {
        return voteRepository.findById(id).filter(vote -> vote.getUser().getId() == userId).orElse(null);
    }

    public boolean delete(int id, int userId) {
        return voteRepository.delete(id, userId) != 0;
    }
}
