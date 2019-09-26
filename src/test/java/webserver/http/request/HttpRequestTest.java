package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.HttpSession;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        final String plainTextRequest = "POST /user/create?id=1 HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Cookie: user=bedi;\n" +
                "\n";

        final InputStream in = new ByteArrayInputStream(plainTextRequest.getBytes());

        httpRequest = HttpRequestFactory.generate(in);
    }

    @Test
    void jSessionId가_없을떄_새로_생성() {
        final HttpSession session = httpRequest.getSession();

        assertThat(session).isNotNull();
    }
}