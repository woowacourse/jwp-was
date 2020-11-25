package server.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.web.request.UrlParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlParametersTest {

    @DisplayName("정상적인 문자열이 들어왔을 때")
    @Test
    void constructorTest() {
        String normal = "a=a&b=b";

        UrlParameters urlParameters = new UrlParameters(normal);

        assertThat(urlParameters.get("a")).isEqualTo("a");
        assertThat(urlParameters.get("b")).isEqualTo("b");
    }

    @DisplayName("&(url delimiter) 만 입력되는 경우 Exception 발생")
    @Test
    void constructorTest2() {
        String abnormal = "&";

        assertThatThrownBy(() -> new UrlParameters(abnormal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비어있습니다.");
    }
}