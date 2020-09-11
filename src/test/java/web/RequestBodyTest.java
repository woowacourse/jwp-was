package web;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    @DisplayName("body 에서 User를 추출할 수 있다.")
    @Test
    void parseBody() {
        String body = "userId=a&password=b&name=c&email=d%40d";
        final User expected = new User("a", "b", "c", "d%40d");

        final RequestBody requestBody = new RequestBody(body);

        final User user = requestBody.parseUser();

        assertThat(user).isEqualTo(expected);
    }

}