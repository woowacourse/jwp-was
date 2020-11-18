package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

public class HttpRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestTest.class);
    private static final String GET_REQUEST_HEADER_NO_PARAMETER = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";
    private static final String GET_REQUEST_HEADER_WITH_PARAMETER = "GET /user/create?userId=javajigi"
            + "&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
    private static final String POST_REQUEST_WITH_BODY = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

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
}
