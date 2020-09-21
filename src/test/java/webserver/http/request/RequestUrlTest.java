package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestUrlTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        String url = "/index?a=1&b=2";
        RequestUrl requestUrl = RequestUrl.from(url);

        assertAll(
            () -> assertThat(requestUrl.getUrl()).isEqualTo("/index"),
            () -> assertThat(requestUrl.getHttpRequestParams().getParameters("a")).contains("1"),
            () -> assertThat(requestUrl.getHttpRequestParams().getParameters("b")).contains("2")
        );
    }

    @DisplayName("쿼리가 빌 경우 생성이 되는 지 확인한다.")
    @Test
    void fromWithNonQuery() {
        String url = "/index";
        RequestUrl requestUrl = RequestUrl.from(url);
        assertAll(
            () -> assertThat(requestUrl.getHttpRequestParams().getParametersMap()).isEmpty(),
            () -> assertThat(requestUrl.getUrl()).isEqualTo("/index")
        );
    }
}