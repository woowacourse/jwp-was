package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RequestLineTest {

    @DisplayName("RequestLine에서 QueryString 추출")
    @Test
    void extractQueryString() {
        String requestUri = "/user/create?userId=javajigi&password=password&name=name&email=javajigi@slipp.net";
        RequestLine requestLine = new RequestLine(null, requestUri);

        assertThat(requestLine.extractQueryString())
                .isEqualTo("userId=javajigi&password=password&name=name&email=javajigi@slipp.net");
    }


    @DisplayName("RequestLine에서 QueryString이 없을 때 예외 발생")
    @Test
    void extractQueryString2() {
        String requestUri = "/user/create";
        RequestLine requestLine = new RequestLine(null, requestUri);

        assertThatThrownBy(() -> requestLine.extractQueryString())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("QueryString을 포함하고 있지 않습니다.");
    }

    @DisplayName("포함되는 RequestLine 인지")
    @Test
    void isMatch() {
        RequestLine actual = new RequestLine(HttpMethod.GET, "user/form.html");

        boolean isMatch = actual.isMatch(new RequestLine(HttpMethod.GET, ".html"));

        assertThat(isMatch).isTrue();
    }

    @DisplayName("포함되지 않은 RequestLine 인지")
    @Test
    void isMatch2() {
        RequestLine actual = new RequestLine(HttpMethod.GET, "user/gg");

        boolean isMatch = actual.isMatch(new RequestLine(HttpMethod.GET, ".user/create"));

        assertThat(isMatch).isFalse();
    }
}