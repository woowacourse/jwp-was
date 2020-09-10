package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("request line을 입력됐을 때 URL 추출")
    @Test
    void extractURLTest() {
        String requestLine = "GET /index.html HTTP/1.1";

        String url = IOUtils.extractURL(requestLine);

        assertThat(url).isEqualTo("/index.html");
    }

    @DisplayName("request를 로그로 남김")
    @Test
    void printRequestTest() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        InputStream in = new ByteArrayInputStream(request.getBytes());

        IOUtils.printRequest(in);
    }

    @DisplayName("")
    @Test
    void extractQueryParamsTest() {
        String url = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

//        String[] queryParams = IOUtils.extractQueryParams(url);

//        assertThat(queryParams).hasSize(4);
    }
}
