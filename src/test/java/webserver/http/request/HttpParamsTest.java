package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.User;

class HttpParamsTest {

    @Test
    void toModel() {
        HttpParams params = HttpParams.of("userId=turtle");
        User expected = new User("turtle", null, null, null);
        User actual = params.toModel(User.class);
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
    }
}