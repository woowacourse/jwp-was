package http.controller;

import http.model.HttpHeaders;
import http.model.HttpRequest;
import http.model.HttpStatus;
import http.model.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListControllerTest {
    @Test
    void 로그인_된_경우() {
        Controller controller = new ListController();
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addHeader("Cookie", "JSESSIONID=123456");
        HttpRequest httpRequest = new HttpRequest(requestLine, null, httpHeaders);

        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 로그인_안된_경우() {
        Controller controller = new ListController();
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, null, httpHeaders);

        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}