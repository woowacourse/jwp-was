package service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import db.DataBase;
import dto.JoinRequestDto;
import dto.LoginRequestDto;
import dto.UserResponseDto;
import exception.UnAuthenticationException;
import model.User;

public class UserService {
    public void login(LoginRequestDto loginRequestDto) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();
        if (Objects.isNull(userId) || Objects.isNull(password)) {
            throw new UnAuthenticationException("아이디 혹은 비밀번호 입력이 잘못되었습니다.");
        }
        User savedUser = DataBase.findUserById(userId);
        if (Objects.isNull(savedUser) || !savedUser.checkPassword(password)) {
            throw new UnAuthenticationException("아이디 혹은 비밀번호 입력이 잘못되었습니다.");
        }
    }

    public void join(JoinRequestDto joinRequestDto) {
        User user = new User(
            joinRequestDto.getUserId(),
            joinRequestDto.getPassword(),
            joinRequestDto.getName(),
            joinRequestDto.getEmail()
        );
        DataBase.addUser(user);
    }

    public List<UserResponseDto> findAll() {
        List<User> users = DataBase.findAll();
        return users.stream()
            .map(UserResponseDto::new)
            .collect(Collectors.toList());
    }
}
