package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpUrlTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "index?a=1&b=2";
        HttpUrl httpUrl = HttpUrl.from(url);

        assertThat(httpUrl.getUrl()).isEqualTo("index");
        assertThat(httpUrl.getHttpRequestParams().getOneParameterValue("a")).isEqualTo("1");
        assertThat(httpUrl.getHttpRequestParams().getOneParameterValue("b")).isEqualTo("2");
    }
}