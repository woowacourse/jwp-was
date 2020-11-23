package model.general;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import model.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CookiesTest {

    @Test
    @DisplayName("Cookies 생성")
    void create() throws IOException {
        String filePath = "src/test/resources/input/get_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Cookies cookies = httpRequest.getCookies();

        assertThat(cookies).isInstanceOf(Cookies.class);
    }

    @Test
    @DisplayName("Cookie 확인")
    void getCookie() throws IOException {
        String filePath = "src/test/resources/input/get_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Cookies cookies = httpRequest.getCookies();

        assertThat(cookies.getCookie("logined")).isInstanceOf(Optional.class);
    }

    @ParameterizedTest
    @DisplayName("로그인 여부 확인")
    @CsvSource(value = {
        "src/test/resources/input/get_api_request.txt:true",
        "src/test/resources/input/post_api_request.txt:false",
    }, delimiter = ':')
    void isLogined(String filePath, boolean expected) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Cookies cookies = httpRequest.getCookies();

        assertThat(cookies.isLogined()).isEqualTo(expected);
    }
}
