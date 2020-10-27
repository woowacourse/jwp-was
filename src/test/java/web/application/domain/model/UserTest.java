package web.application.domain.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("유저의 정보를 복사하여 새로운 객체를 만든다.")
    @Test
    void testClone() {
        User user = User.builder()
            .name("pobi")
            .email("test@naver.com")
            .password("1234")
            .userId("pobibi")
            .build();

        User actual = user.clone();

        assertAll(
            () -> assertThat(user == actual).isFalse(),
            () -> assertThat(user.getName()).isEqualTo(actual.getName()),
            () -> assertThat(user.getEmail()).isEqualTo(actual.getEmail()),
            () -> assertThat(user.getPassword()).isEqualTo(actual.getPassword()),
            () -> assertThat(user.getUserId()).isEqualTo(actual.getUserId())
        );
    }
}