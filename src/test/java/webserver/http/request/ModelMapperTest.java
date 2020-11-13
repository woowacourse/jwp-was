package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.User;
import webserver.servlet.ModelMapper;

class ModelMapperTest {
    @Test
    void toModel() {
        HttpParams params = HttpParams.of("userId=turtle");
        User expected = new User("turtle", null, null, null);
        User actual = ModelMapper.toModel(User.class, params);
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
    }
}