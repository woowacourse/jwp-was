package utils;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @DisplayName("readBodyData 정상 실행")
    @Test
    public void readBodyDataTest() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readBodyData(br, data.length()));
    }

    @DisplayName("Http Request에서 header만 정상적으로 읽어오는지 확인")
    @Test
    void readHeaderDataTest() throws IOException {
        final String data = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 59\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";
        final StringReader stringReader = new StringReader(data);
        final BufferedReader bufferedReader = new BufferedReader(stringReader);

        assertThat(IOUtils.readHeaderData(bufferedReader)).hasSize(6);
    }
}
