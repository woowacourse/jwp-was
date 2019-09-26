package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void parseData() throws Exception {
        String expected = "Request URL: http://localhost:8080/\n" +
                "Request Method: GET\n" +
                "Status Code: 200 OK\n" +
                "Remote Address: [::1]:8080\n" +
                "Referrer Policy: no-referrer-when-downgrade\n";
        StringReader sr = new StringReader(expected);
        BufferedReader br = new BufferedReader(sr);

        assertThat(IOUtils.parseData(br)).isEqualTo(expected);
    }

    @Test
    public void parseAllLineWhenPost() throws IOException {
        List<String> actual = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );

        BufferedReader bufferedReader = Common.getBufferedReader("HTTP_POST_USER_CREATE.txt");
        List<String> lines = IOUtils.parseAllLine(bufferedReader);
        assertThat(lines).isEqualTo(actual);
    }

    @Test
    public void parseAllLineWhenBodyDoesntExist() throws IOException {
        List<String> actual = Arrays.asList(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );

        BufferedReader bufferedReader = Common.getBufferedReader("HTTP_GET_INDEX_HTML.txt");
        List<String> lines = IOUtils.parseAllLine(bufferedReader);
        assertThat(lines).isEqualTo(actual);
    }
}
