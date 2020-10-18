package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {

    private static final String TEST_REQUEST_DIRECTORY = "./src/test/resources/request/";

    @DisplayName("HttpRequest GET 요청")
    @Test
    void request_GET() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @DisplayName("HttpRequest POST 요청, Param 없음")
    @Test
    void request_POST_WithoutParam() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_POST_Create_User.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getBodyParameter("userId")).isEqualTo("javajigi");
    }

    @DisplayName("HttpRequest POST 요청, Param 있음")
    @Test
    void request_POST_WithParam() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_POST_With_Param.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("id")).isEqualTo("1");
        assertThat(request.getParameter("name")).isEqualTo("bingbong");
        assertThat(request.getBodyParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getBodyParameter("password")).isEqualTo("password");
        assertThat(request.getBodyParameter("name")).isEqualTo("JaeSung");
    }

    @DisplayName("HttpRequest TRACE 요청, 지원하지 않음")
    @Test
    void isMethodSupported_NotSupport() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_TRACE.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.isMethodSupported()).isFalse();
    }

    @DisplayName("HttpRequest 쿠키 조회")
    @Test
    void getCookies() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + "Http_Request_GET_Cookie_Login_True.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest(br);

        assertThat(request.getCookies()).hasSize(1);
    }
}
