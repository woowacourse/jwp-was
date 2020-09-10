package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

import http.RequestHeader;
import http.RequestLine;
import http.RequestMethod;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("request line을 객체로 변환하는 메서드")
    @Test
    void ofReqeustLineTest() {
        RequestLine requestLine = IOUtils.ofRequestLine("GET /index.html HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getUrl()).isEqualTo("/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP/1.1");
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

    @DisplayName("requestHeader 만들기 테스트")
    @Test
    void ofRequestHeaderTest() throws IOException {
        String request = "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        RequestHeader requestHeader = IOUtils.ofRequestHeader(br);

        assertThat(requestHeader.getRequestHeaders()).hasSize(3);
    }
}
