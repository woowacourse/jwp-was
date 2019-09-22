package webserver.http.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParametersParserTest {

    @Test
    void 생성_테스트() {
        // given & when
        final Map<String, String> parameters = ParameterParser.parse("userId=javajigi&password=password&email=javajigi%40slipp.net");

        // then
        assertThat(parameters).hasSize(3)
                .containsEntry("userId", "javajigi")
                .containsEntry("password", "password")
                .containsEntry("email","javajigi@slipp.net");
    }

    @Test
    void 빈값_일경우() {
        // given & when
        final Map<String, String> parameters = ParameterParser.parse("");

        // then
        assertThat(parameters).hasSize(0);
    }
}