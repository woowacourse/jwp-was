package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import webserver.http.request.HttpParams;

class ModelMapperTest {
    @Test
    void toModel() {
        HttpParams params = HttpParams.of("userId=turtle");
        UserModel expected = new UserModel("turtle", null, null, null);
        UserModel actual = ModelMapper.toModel(UserModel.class, params);
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
    }
}