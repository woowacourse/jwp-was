package http.supoort.converter.request;

import http.exceptions.IllegalHttpRequestException;
import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestMessageConverterTest {
    private RequestMessageConverter requestConverter = new RequestMessageConverter();

    @Test
    void 올바른_입력_파싱_확인() {
        InputStream in = new ByteArrayInputStream("GET /index.html HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        ServletRequest request = ServletRequest.builder()
                .requestLine(HttpMethod.GET, "/index.html", "HTTP/1.1")
                .build();

        ServletRequest parsedRequest = requestConverter.parse(in);
        assertThat(parsedRequest.getHttpMethod()).isEqualTo(request.getHttpMethod());
        assertThat(parsedRequest.getHttpUri()).isEqualTo(request.getHttpUri());
    }

    @Test
    void 올바른_입력_파라미터_존재_파싱_확인() {
        InputStream in = new ByteArrayInputStream("GET /index.html?name=coogi&age=25 HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", "coogi");
        parameters.put("age", "25");

        ServletRequest request = ServletRequest.builder()
                .requestLine(HttpMethod.GET, "/index.html", "HTTP/1.1")
                .params(parameters)
                .build();

        ServletRequest parsedRequest = requestConverter.parse(in);

        assertThat(parsedRequest.getHttpMethod()).isEqualTo(request.getHttpMethod());
        assertThat(parsedRequest.getHttpUri()).isEqualTo(request.getHttpUri());
        assertThat(parsedRequest.getParameter("name")).isEqualTo("coogi");
        assertThat(parsedRequest.getParameter("age")).isEqualTo("25");
    }

    @Test
    void 올바른_HTTP_요청_아닌_경우1() {
        InputStream in = new ByteArrayInputStream("GET /index.html?name=coogi&age=25HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        assertThatThrownBy(() -> requestConverter.parse(in)).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 올바른_HTTP_요청_아닌_경우2() {
        InputStream in = new ByteArrayInputStream("GET/index.html?name=coogi&age=25 HTTP/1.1\r\nHost: localhost:8080/".getBytes());
        assertThatThrownBy(() -> requestConverter.parse(in)).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 올바른_HTTP_요청_아닌_경우3() {
        InputStream in = new ByteArrayInputStream("".getBytes());
        assertThatThrownBy(() -> requestConverter.parse(in)).isInstanceOf(IllegalHttpRequestException.class);
    }

    @Test
    void 쿼리스트링_포함_POST_요청() {
        InputStream in = new ByteArrayInputStream(("POST /user/create?id=1 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 46\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=javajigi&password=password&name=JaeSung\r\n").getBytes());
        ServletRequest request = requestConverter.parse(in);

        assertThat(request.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getHttpUri().getResourceLocation()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    void 쿠키헤더_포함_GET_요청() {
        InputStream in = new ByteArrayInputStream(("GET /user/create?id=1 HTTP/1.1\r\n" +
                "Cookie: logined=true\r\n").getBytes());

        ServletRequest request = requestConverter.parse(in);

        assertThat(request.hasCookie()).isTrue();
        assertThat(request.getCookie("logined")).isEqualTo("true");
    }

    @Test
    void 쿠키헤더_여러개_포함_GET_요청() {
        InputStream in = new ByteArrayInputStream(("GET /user/create?id=1 HTTP/1.1\r\n" +
                "Cookie: logined=true; another=something\r\n").getBytes());

        ServletRequest request = requestConverter.parse(in);

        assertThat(request.hasCookie()).isTrue();
        assertThat(request.getCookies().size()).isEqualTo(2);
        assertThat(request.getCookie("logined")).isEqualTo("true");
        assertThat(request.getCookie("another")).isEqualTo("something");
    }

    @Test
    void 쿠키헤더_포함_POST_요청() {
        InputStream in = new ByteArrayInputStream(("POST /user/create?id=1 HTTP/1.1\r\n" +
                "Cookie: logined=true\r\n" +
                "Content-Length: 46\r\n" +
                "\r\n" +
                "userId=javajigi&password=password&name=JaeSung\r\n").getBytes());

        ServletRequest request = requestConverter.parse(in);

        assertThat(request.hasCookie()).isTrue();
        assertThat(request.getCookies().size()).isEqualTo(1);
        assertThat(request.getCookie("logined")).isEqualTo("true");
        assertThat(request.getHeaders().containsKey("Cookie")).isFalse();
    }

    @Test
    void 쿠키헤더_여러개_포함_POST_요청() {
        InputStream in = new ByteArrayInputStream(("POST /user/create?id=1 HTTP/1.1\r\n" +
                "Cookie: logined=true; another=something\r\n" +
                "Content-Length: 46\r\n" +
                "\r\n" +
                "userId=javajigi&password=password&name=JaeSung\r\n").getBytes());

        ServletRequest request = requestConverter.parse(in);

        assertThat(request.hasCookie()).isTrue();
        assertThat(request.getCookies().size()).isEqualTo(2);
        assertThat(request.getCookie("logined")).isEqualTo("true");
        assertThat(request.getCookie("another")).isEqualTo("something");
        assertThat(request.getHeaders().containsKey("Cookie")).isFalse();

    }
}