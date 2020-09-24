package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.RequestLine;

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

}