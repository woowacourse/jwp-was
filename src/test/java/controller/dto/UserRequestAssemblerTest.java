package controller.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.USER_EMAIL;
import static util.Constants.USER_ID;
import static util.Constants.USER_NAME;
import static util.Constants.USER_PASSWORD;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRequestAssemblerTest {

    @DisplayName("파라미터가 다 존재할 때, UserRequest 생성")
    @Test
    void assemble_ExistsAllParameter_ReturnUserRequest() {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put(USER_ID, USER_ID);
        keyValues.put(USER_PASSWORD, USER_PASSWORD);
        keyValues.put(USER_NAME, USER_NAME);
        keyValues.put(USER_EMAIL, USER_EMAIL);
        UserRequest userRequest = UserRequestAssembler.assemble(keyValues);

        assertThat(userRequest.getUserId()).isEqualTo(USER_ID);
        assertThat(userRequest.getPassword()).isEqualTo(USER_PASSWORD);
        assertThat(userRequest.getName()).isEqualTo(USER_NAME);
        assertThat(userRequest.getEmail()).isEqualTo(USER_EMAIL);
    }

    @DisplayName("파라미터가 존재하지 않을 때, null값이 들어감")
    @Test
    void assemble_NotExistsAllParameter_ReturnUserRequest() {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put(USER_ID, USER_ID);
        keyValues.put(USER_NAME, USER_NAME);

        UserRequest userRequest = UserRequestAssembler.assemble(keyValues);
        assertThat(userRequest.getUserId()).isEqualTo(USER_ID);
        assertThat(userRequest.getPassword()).isEqualTo(null);
        assertThat(userRequest.getName()).isEqualTo(USER_NAME);
        assertThat(userRequest.getEmail()).isEqualTo(null);
    }
}