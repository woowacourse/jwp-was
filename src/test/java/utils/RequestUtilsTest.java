package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class RequestUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    // TODO: 2020/09/09 html이 아닌 css, js 파일에 대한 테스트 추가
    @Test
    @DisplayName("RequestHeader에서 올바른 파일명을 추출한다")
    void getFilePathInRequestHeaderTest() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET /index.html HTTP/1.1");
        stringBuilder.append("Host: localhost:8080");
        stringBuilder.append("Connection: keep-alive");
        stringBuilder.append("Accept: */*)");
        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            Assertions.assertThat(RequestUtils.getFilePathInRequestHeader(bufferedReader)).isEqualTo("templates/index.html");
        } catch(IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
