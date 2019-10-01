package http.request;

import http.common.Cookie;
import http.common.HttpSession;
import org.junit.jupiter.api.Test;
import webserver.SessionHandler;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void getCookieTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "logined_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        Cookie cookie = new Cookie("logined", "true");
        assertThat(request.getCookie("logined")).isEqualTo(cookie);
    }

    @Test
    void 세션이_존재하지_않는다면_생성하여_리턴한다() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "get_Http_Header.txt"));
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        assertThat(request.getSession()).isInstanceOf(HttpSession.class);
    }

    @Test
    void 존재하는_세션을_그대로_리턴하는지_확인() throws IOException {
        String requestInput = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: sessionId=";
        HttpSession session = new HttpSession();
        requestInput += session.getId();
        InputStream in = new ByteArrayInputStream(requestInput.getBytes(UTF_8));

        SessionHandler.getInstance().addSession(session.getId(), session);
        HttpRequest request = HttpRequestFactory.createHttpRequest(in);
        assertThat(request.getSession().getId()).isEqualTo(session.getId());
    }
}
