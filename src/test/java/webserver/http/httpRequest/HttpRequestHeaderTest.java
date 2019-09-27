package webserver.http.httpRequest;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest extends WebTestForm {

    @Test
    void 객체_생성후_HOST_확인_테스트() {
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.create(getHttpDefaultHeader());
        String host = httpRequestHeader.getHost();
        assertThat(host).isEqualTo("localhost:8080");
    }
}