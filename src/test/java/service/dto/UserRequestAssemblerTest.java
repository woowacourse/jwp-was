package service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserRequestAssemblerTest {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @DisplayName("파라미터가 다 존재할 때, UserRequest 생성")
    @Test
    void assemble_ExistsAllParameter_ReturnUserRequest() {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put(USER_ID, USER_ID);
        keyValues.put(PASSWORD, PASSWORD);
        keyValues.put(NAME, NAME);
        keyValues.put(EMAIL, EMAIL);
        UserRequest userRequest = UserRequestAssembler.assemble(keyValues);

        assertThat(userRequest.getUserId()).isEqualTo(USER_ID);
        assertThat(userRequest.getPassword()).isEqualTo(PASSWORD);
        assertThat(userRequest.getName()).isEqualTo(NAME);
        assertThat(userRequest.getEmail()).isEqualTo(EMAIL);
    }

    @DisplayName("파라미터가 존재하지 않을 때, null값이 들어감")
    @Test
    void assemble_NotExistsAllParameter_ReturnUserRequest() {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put(USER_ID, USER_ID);
        keyValues.put(NAME, NAME);

        UserRequest userRequest = UserRequestAssembler.assemble(keyValues);
        assertThat(userRequest.getUserId()).isEqualTo(USER_ID);
        assertThat(userRequest.getPassword()).isEqualTo(null);
        assertThat(userRequest.getName()).isEqualTo(NAME);
        assertThat(userRequest.getEmail()).isEqualTo(null);
    }
}