package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @Test
    void 공백라인_포함_읽기() {
        String message = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 42\n" +
                "\n" +
                "something";

        InputStream inputStream = new ByteArrayInputStream(message.getBytes());
        List<String> strings = IOUtils.readData(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(strings.get(strings.size() - 1)).isNotEqualTo("something");
    }

    @Test
    void 공백라인_없는_메시지() {
        String message = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 42\n";

        InputStream inputStream = new ByteArrayInputStream(message.getBytes());
        List<String> strings = IOUtils.readData(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(strings.get(strings.size() - 1)).isNotEqualTo("");
    }
}
