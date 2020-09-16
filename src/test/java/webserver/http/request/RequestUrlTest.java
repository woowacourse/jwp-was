package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestUrlTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "index?a=1&b=2";
        RequestUrl requestUrl = RequestUrl.from(url);

        assertThat(requestUrl.getUrl()).isEqualTo("index");
        assertThat(requestUrl.getHttpRequestParams().getOneParameterValue("a")).isEqualTo("1");
        assertThat(requestUrl.getHttpRequestParams().getOneParameterValue("b")).isEqualTo("2");
    }
}