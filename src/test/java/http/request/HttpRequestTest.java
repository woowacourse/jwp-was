package http.request;

import http.common.Cookie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
}
