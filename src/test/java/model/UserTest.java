package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    void 아이디_누락된_경우() {
        assertThatThrownBy(() -> User.builder()
                .password("pass")
                .name("name")
                .email("mail@mail.com")
                .build()).isInstanceOf(IllegalUserArgsException.class);
    }

    @Test
    void 패스워드_누락된_경우() {
        assertThatThrownBy(() -> User.builder()
                .userId("id")
                .name("name")
                .email("mail@mail.com")
                .build()).isInstanceOf(IllegalUserArgsException.class);
    }

    @Test
    void 이름_누락된_경우() {
        assertThatThrownBy(() -> User.builder()
                .userId("id")
                .password("pass")
                .email("mail@mail.com")
                .build()).isInstanceOf(IllegalUserArgsException.class);
    }

    @Test
    void 이메일_누락된_경우() {
        assertThatThrownBy(() -> User.builder()
                .userId("id")
                .password("pass")
                .name("name")
                .build()).isInstanceOf(IllegalUserArgsException.class);
    }

    @Test
    void 필드에_빈_문자열_넣을_경우() {
        assertThatThrownBy(() -> User.builder()
                .userId("")
                .password("pass")
                .name("name")
                .email("mail@mail.com")
                .build()).isInstanceOf(IllegalUserArgsException.class);
    }
}