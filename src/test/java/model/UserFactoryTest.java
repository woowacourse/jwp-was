package model;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import model.user.User;
import model.user.UserCreateException;
import model.user.UserFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserFactoryTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void 유저_생성_테스트() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "post_Http_Header.txt"));
        HttpRequest httprequest = HttpRequestFactory.createHttpRequest(in);
        User user = new User("seon", "password", "sos", "sos@sos.sos");
        assertThat(UserFactory.createUser(httprequest)).isEqualTo(user);
    }

    @Test
    void 유저_생성_실패_테스트() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "invalid_user_post_Http_Header.txt"));
        HttpRequest httprequest = HttpRequestFactory.createHttpRequest(in);
        assertThrows(UserCreateException.class, () -> UserFactory.createUser(httprequest));
    }
}