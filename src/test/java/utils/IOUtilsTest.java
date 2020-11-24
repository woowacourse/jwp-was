package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.wootecat.dongle.utils.IOUtils;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

    @DisplayName("bufferedReader 학습 테스트 readline")
    @Test
    void readLineData() throws IOException {
        String requestData = "GET /index.html HTTP1/1\r\ncontent-type: application/json;" +
                "charset=utf-8\r\naccept: application/json;charset=utf-8\r\n\r\n" +
                "name=one&age=15\r\n";
        StringReader sr = new StringReader(requestData);
        BufferedReader br = new BufferedReader(sr);

        logger.info(br.readLine());
        logger.info(br.readLine());
        logger.info(br.readLine());
        logger.info(br.readLine());
        logger.info(br.readLine());
        logger.info(br.readLine());
        logger.info(br.readLine());
    }
}
