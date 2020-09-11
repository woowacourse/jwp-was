package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @DisplayName("데이터 정상으로 읽는지 확인")
    @Test
    public void readData_Success() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        String parseBody = IOUtils.readData(br, data.length());
        br.close();
        sr.close();

        logger.debug("parse body : {}", parseBody);
        assertThat(parseBody).isEqualTo(data);
    }

    @DisplayName("데이터 길이가 contentLength보다 큰 경우 - contentLength까지만 body로 읽음")
    @Test
    public void readData_IsMoreDataLengthThanContentLength() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        int contentLength = data.length() - 1;
        String parseBody = IOUtils.readData(br, contentLength);
        br.close();
        sr.close();

        logger.debug("parse body : {}", parseBody);
        assertThat(parseBody).isEqualTo(data.substring(0, contentLength));
    }

    @DisplayName("데이터 길이가 contentLength보다 작은 경우 - 차이만큼 추가로 읽음")
    @Test
    public void readData_IsLessDataLengthThanContentLength() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        int contentLength = data.length() + 1;
        String parseBody = IOUtils.readData(br, contentLength);
        br.close();
        sr.close();

        logger.debug("parse body : {}", parseBody);
        assertThat(parseBody.length()).isEqualTo(contentLength);
        assertThat(parseBody.substring(0, data.length())).isEqualTo(data);
    }
}
