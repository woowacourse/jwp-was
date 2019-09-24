package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestFactoryTest {

    @Test
    void 생성_테스트() {
        // given
        final String plainTextRequest = "POST /user/create?id=1 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=JaeSung";

        final InputStream in = new ByteArrayInputStream(plainTextRequest.getBytes());

        // when
        final HttpRequest httpRequest = HttpRequestFactory.generate(in);

        // then
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpRequest.getHeader(HttpHeaders.HOST)).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader(HttpHeaders.CONNECTION)).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader(HttpHeaders.ACCEPT)).isEqualTo("*/*");
        assertThat(httpRequest.getParameter("id")).isEqualTo("1");
        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("JaeSung");
    }

    @Test
    void Body는_없고_쿼리스트링만_있는_경우() {
        // given
        final String plainTextRequest = "GET /user/create?id=1 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n";

        final InputStream in = new ByteArrayInputStream(plainTextRequest.getBytes());

        // when
        final HttpRequest httpRequest = HttpRequestFactory.generate(in);

        // then
        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
        assertThat(httpRequest.getHeader(HttpHeaders.HOST)).isEqualTo("localhost:8080");
        assertThat(httpRequest.getHeader(HttpHeaders.CONNECTION)).isEqualTo("keep-alive");
        assertThat(httpRequest.getHeader(HttpHeaders.ACCEPT)).isEqualTo("*/*");
        assertThat(httpRequest.getParameter("id")).isEqualTo("1");
        assertThat(httpRequest.sizeOfParameters()).isEqualTo(1);
    }
}
