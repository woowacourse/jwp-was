package net.slipp.application;

import net.slipp.application.exception.DuplicatedUserIdException;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import net.slipp.presentation.dto.JoinRequest;
import net.slipp.presentation.dto.JoinResponse;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public JoinResponse create(JoinRequest joinRequest) {
        User user = joinRequest.toEntity();
        String requestUserId = user.getUserId();
        if (userRepository.isExistUserId(requestUserId)) {
            throw new DuplicatedUserIdException(requestUserId);
        }
        userRepository.addUser(user);
        return new JoinResponse(user.getUserId());
    }
}
