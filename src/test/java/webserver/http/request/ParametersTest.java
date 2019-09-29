package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.utils.HttpUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParametersTest {

    @Test
    void 생성할때_문자열_파싱_되는지_확인() {
        // given & when
        final Parameters parameters = new Parameters("userId=javajigi&password=password&email=javajigi%40slipp.net");

        // then
        assertThat(3).isEqualTo(parameters.size());
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isEqualTo("password");
        assertThat(parameters.get("email")).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void queryString_빈값_일경우() {
        // given & when
        final Parameters parameters = new Parameters("");

        // then
        assertThat(0).isEqualTo(parameters.size());
    }

    @Test
    void addByString_파싱_되는지_확인() {
        // given
        final Parameters parameters = new Parameters();

        // when
        parameters.addByString("userId=javajigi&password=password&email=javajigi%40slipp.net");

        // then
        assertThat(3).isEqualTo(parameters.size());
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isEqualTo("password");
        assertThat(parameters.get("email")).isEqualTo("javajigi@slipp.net");
    }
}