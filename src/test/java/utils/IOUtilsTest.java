package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("Map형식의 Request Header를 추출한다.")
    @Test
    void readRequestHeaders() throws IOException {
        // given
        String requestHeader = "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 59\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*";

        StringReader stringReader = new StringReader(requestHeader);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        // when
        Map<String, String> actual = IOUtils.readRequestHeaders(bufferedReader);
        Map<String, String> expected = new HashMap<>();
        expected.put("Host", "localhost:8080");
        expected.put("Connection", "keep-alive");
        expected.put("Content-Length", "59");
        expected.put("Content-Type", "application/x-www-form-urlencoded");
        expected.put("Accept", "*/*");

        // then
        assertThat(actual).isEqualTo(expected);
    }

}
