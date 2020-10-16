package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.request.Parameters;

class ParametersTest {
    @DisplayName("빈 문자열이 아닐 경우 객체를 생성")
    @Test
    void from() {
        assertThat(Parameters.from("username=tichi&password=tichi")).isInstanceOf(
                Parameters.class);
    }

    @DisplayName("빈 문자열일 경우 객체를 생성")
    @Test
    void from_EmptyString() {
        assertThat(Parameters.from("")).isInstanceOf(Parameters.class);
    }
}
