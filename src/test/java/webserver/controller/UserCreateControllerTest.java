package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.httpRequest.HttpRequestBody;
import webserver.http.httpRequest.HttpRequestHeader;
import webserver.http.httpRequest.HttpStartLine;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserCreateControllerTest {

    @Test
    void 정상케이스_출력_테스트() {
        UserCreateController userCreateController = new UserCreateController();
        String startLine = "POST / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());

        String body = "userId=starkim06&email=alswns@naver.com&name=alswns&password=temp";
        HttpRequestBody httpRequestBody = HttpRequestBody.of(body);

        HttpRequest httpRequest = new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
        assertThat(userCreateController.service(httpRequest)).isEqualTo("/redirect:/index.html");
    }

    @Test
    void 지원하지않는_메소드_테스트() {
        UserCreateController userCreateController = new UserCreateController();
        String startLine = "GET / HTTP1.1";
        HttpStartLine httpStartLine = HttpStartLine.of(startLine);

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(new HashMap<>());

        String body = "userId=starkim06&email=alswns@naver.com&name=alswns&password=temp";
        HttpRequestBody httpRequestBody = HttpRequestBody.of(body);

        HttpRequest httpRequest = new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);
        assertThrows(IllegalArgumentException.class, () -> userCreateController.service(httpRequest));
    }
}