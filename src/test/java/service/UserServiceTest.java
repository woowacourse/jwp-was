package service;

import db.DataBase;
import http.RequestBody;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Test
    void createUserTest() {
        RequestBody requestBody = mock(RequestBody.class);
        when(requestBody.getValue("userId")).thenReturn("javajigi");
        when(requestBody.getValue("password")).thenReturn("password");
        when(requestBody.getValue("name")).thenReturn("박재성");
        when(requestBody.getValue("email")).thenReturn("javajigi@slipp.net");

        UserService userService = new UserService();
        userService.createUser(requestBody);

        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }
}