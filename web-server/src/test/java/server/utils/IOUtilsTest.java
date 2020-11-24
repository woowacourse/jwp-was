package server.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.web.controller.RequestMapping;
import server.web.request.HttpMethod;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequestMapping(uri = "default", httpMethod = HttpMethod.GET)
public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("BufferedReader에서 Header 부분을 List<String>으로 변환하기")
    @Test
    void readHeader() throws IOException {
        String request = "Host: localhost:8080\n"
                + "Connection: keep-alive\n" +
                "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));

        List<String> header = IOUtils.readHeader(br);

        assertThat(header.get(0)).isEqualTo("Host: localhost:8080");
        assertThat(header.get(1)).isEqualTo("Connection: keep-alive");
        assertThat(header).hasSize(2);
    }
}
