package net.slipp.application;

import net.slipp.application.exception.AuthenticationFailException;
import net.slipp.application.exception.DuplicatedUserIdException;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import net.slipp.presentation.dto.JoinRequest;
import net.slipp.presentation.dto.JoinResponse;
import net.slipp.presentation.dto.LoginRequest;
import net.slipp.presentation.dto.UsersResponse;

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

    public void login(LoginRequest loginRequest) {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        User user = userRepository.findUserById(userId).orElseThrow(AuthenticationFailException::new);
        if (!user.hasPassword(password)) {
            throw new AuthenticationFailException();
        }
    }

    public UsersResponse findAll() {
        return UsersResponse.ofList(userRepository.findAll());
    }
}
