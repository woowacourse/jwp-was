package model.service;

import http.request.RequestParams;
import model.db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    @DisplayName("유저 save 테스트")
    @Test
    void saveUser() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "id");
        params.put("password", "1234");
        params.put("name", "ordincode");
        params.put("email", "email@email");

        UserService.saveUser(new RequestParams(params), new DataOutputStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));

        assertThat(DataBase.findAll().size()).isNotZero();
    }
}
