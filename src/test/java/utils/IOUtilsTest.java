package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
    void readRequestUrl_NonParams() throws IOException {
        // given
        String requestHeader = "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 59\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*";

        StringReader stringReader = new StringReader(requestHeader);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        // when
        Map<String, String> actual = IOUtils.readRequestHeader(bufferedReader);

        // then
        assertAll(
                () -> assertThat(actual.get("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(actual.get("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(actual.get("Content-Length")).isEqualTo("59"),
                () -> assertThat(actual.get("Content-Type")).isEqualTo(
                        "application/x-www-form-urlencoded"),
                () -> assertThat(actual.get("Accept")).isEqualTo("*/*")
        );
    }

}
