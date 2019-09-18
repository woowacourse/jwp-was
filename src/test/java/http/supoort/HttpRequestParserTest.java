package http.supoort;

import http.model.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestParserTest {

    @Test
    void 올바른_입력_파싱_확인() {
        InputStream in = new ByteArrayInputStream("GET /index.html HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, new HttpUri("/index.html"), null, new HttpProtocol("HTTP/1.1"));
        HttpRequest parsedRequest = HttpRequestParser.parse(in);
        assertThat(parsedRequest.getHttpMethod()).isEqualTo(httpRequest.getHttpMethod());
        assertThat(parsedRequest.getHttpUri()).isEqualTo(httpRequest.getHttpUri());
    }

    @Test
    void 올바른_입력_파라미터_존재_파싱_확인() {
        InputStream in = new ByteArrayInputStream("GET /index.html?name=coogi&age=25 HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "coogi");
        parameters.put("age", "25");

        HttpParameters httpParameters = new HttpParameters(parameters);
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, new HttpUri("/index.html"), httpParameters, new HttpProtocol("HTTP/1.1"));
        HttpRequest parsedRequest = HttpRequestParser.parse(in);

        assertThat(parsedRequest.getHttpMethod()).isEqualTo(httpRequest.getHttpMethod());
        assertThat(parsedRequest.getHttpUri()).isEqualTo(httpRequest.getHttpUri());
        assertThat(parsedRequest.getParameters()).isEqualTo(httpParameters);
    }

    @Test
    void 올바른_HTTP_요청_아닌_경우1() {
        InputStream in = new ByteArrayInputStream("GET /index.html?name=coogi&age=25HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        assertThatThrownBy(()->HttpRequestParser.parse(in)).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 올바른_HTTP_요청_아닌_경우2() {
        InputStream in = new ByteArrayInputStream("GET/index.html?name=coogi&age=25 HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        assertThatThrownBy(()->HttpRequestParser.parse(in)).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 올바른_HTTP_요청_아닌_경우3() {
        InputStream in = new ByteArrayInputStream("".getBytes());
        assertThatThrownBy(()->HttpRequestParser.parse(in)).isInstanceOf(IllegalHttpRequestException.class);
    }
}