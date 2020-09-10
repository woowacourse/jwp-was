package service;

import org.junit.jupiter.api.Test;

public class UserServiceTest {

    @Test
    void create() {
        UserService userService = UserService.getInstance();
        userService.create("/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}