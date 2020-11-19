package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaderType;

import java.io.*;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestTest.class);
    private static final String GET_REQUEST_HEADER_NO_PARAMETER = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";

    private String testDirectory = "./src/test/resources/";

    @DisplayName("HTTP Request Header의 모든 라인을 출력한다.")
    @Test
    void printAllHttpRequestHeaderTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(GET_REQUEST_HEADER_NO_PARAMETER.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (!"".equals(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            LOGGER.info(line);
            line = bufferedReader.readLine();
        }
    }

    @DisplayName("GET 요청에 대해 HttpRequest 객체 생성")
    @Test
    void requestGetTest() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertAll(
                () -> assertThat(request.getMethod()).isEqualTo("GET"),
                () -> assertThat(request.getUrl().getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getHeader(HttpHeader.of(HttpHeaderType.CONNECTION))).isEqualTo("keep-alive"),
                () -> assertThat(request.getQueryParameter("userId")).isEqualTo("javajigi")
        );
    }

    @DisplayName("POST 요청에 대해 HttpRequest 객체 생성")
    @Test
    void requestPostTest() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertAll(
                () -> assertThat(request.getMethod()).isEqualTo("POST"),
                () -> assertThat(request.getUrl().getPath()).isEqualTo("/user/create"),
                () -> assertThat(request.getHeader(HttpHeader.of(HttpHeaderType.CONNECTION))).isEqualTo("keep-alive"),
                () -> assertThat(request.getBodyParameter("userId")).isEqualTo("javajigi")
        );
    }
}
