package utils;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUtilsTest {

    @DisplayName("body 에서 User를 추출할 수 있다.")
    @Test
    void parseBody() {
        String body = "userId=a&password=b&name=c&email=d%40d";
        final User expected = new User("a", "b", "c", "d%40d");

        final User user = RequestUtils.parseBody(body);

        assertThat(user).isEqualTo(expected);
    }
}