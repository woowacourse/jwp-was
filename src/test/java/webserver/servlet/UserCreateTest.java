package webserver.servlet;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import webserver.domain.request.HttpRequest;

class UserCreateTest {
    @DisplayName("회원가입에 대한 GET 요청이 들어오면 파라미터의 값으로 회원을 생성한다.")
    @Test
    void get() throws IOException {
        File file = new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequest.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        HttpRequest httpRequest = HttpRequest.of(br);

        Servlet servlet = new UserCreate();
        servlet.get(httpRequest);

        User persistUser = DataBase.findUserById("javajigi");
        assertThat(persistUser.getPassword()).isEqualTo("password");
        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("회원가입에 대한 POST 요청이 들어오면 바디의 값으로 회원을 생성한다.")
    @Test
    void post() throws IOException {
        File file = new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/PostRequest.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        HttpRequest httpRequest = HttpRequest.of(br);

        Servlet servlet = new UserCreate();
        servlet.post(httpRequest);

        User persistUser = DataBase.findUserById("javajigi");
        assertThat(persistUser.getPassword()).isEqualTo("password");
        assertThat(persistUser.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(persistUser.getEmail()).isEqualTo("javajigi%40slipp.net");
    }
}