package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.RequestLine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestLineTest {

    @DisplayName("RequestLine에서 QueryString 추출")
    @Test
    void extractQueryString() {
        String requestUri = "/user/create?userId=javajigi&password=password&name=name&email=javajigi@slipp.net";
        RequestLine requestLine = RequestLine.of(null, requestUri);

        assertThat(requestLine.getData())
                .isEqualTo("userId=javajigi&password=password&name=name&email=javajigi@slipp.net");
    }

}