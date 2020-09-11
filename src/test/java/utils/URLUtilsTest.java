package utils;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLUtilsTest {

    @DisplayName("requestLine 에서 path를 추출할 수 있다.")
    @Test
    void parseURL() {
        String requestLine = "GET /index.html?a=a HTTP/1.1";
        String path = URLUtils.parseURL(requestLine);

        assertThat(path).isEqualTo("/index.html");
    }

    @DisplayName("requestLine 에서 파라미터들을 추출할 수 있다.")
    @Test
    void parseUser() {
        String requestLine = "GET /user/create?userId=a&password=b&name=c&email=d%40d HTTP/1.1";
        final User expected = new User("a", "b", "c", "d%40d");

        final User user = URLUtils.parseUser(requestLine);

        assertThat(user).isEqualTo(expected);
    }
}