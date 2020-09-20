package utils;

import domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {

    @DisplayName("input data에서 User 객체로 변환")
    @Test
    void createUser() {
        Map<String, String> inputs = new HashMap<>();
        inputs.put("userId", "javajigi");
        inputs.put("password", "password");
        inputs.put("name", "name");
        inputs.put("email", "javajigi@slipp.net");

        User actual = UserMapper.createUser(inputs);
        User expected = new User("javajigi", "password", "name", "javajigi@slipp.net");

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


}