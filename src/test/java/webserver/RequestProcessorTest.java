package webserver;

import http.Request;
import http.RequestHeader;
import http.ResponseHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestProcessorTest {
    private RequestProcessor requestProcessor;

    @BeforeEach
    void setUp() {
        requestProcessor = new RequestProcessor();
    }

    @Test
    @DisplayName("Status Code 200 Response를 생성한다")
    public void process200() {
        Request request = new Request(new RequestHeader("GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*"));

        assertThat(requestProcessor.process(request)).isEqualTo(
                new ResponseHeader(200, "html", null, "/index.html"));
    }

    @Test
    @DisplayName("Status Code 302 Response를 생성한다")
    public void process302() {
        Request request = new Request(new RequestHeader("POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*"));

        assertThat(requestProcessor.process(request)).isEqualTo(
                new ResponseHeader(302, "html", "/index.html", "/user/create"));
    }
}