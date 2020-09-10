package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;

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

    @Test
    public void decode() {
        String encoded = "%ED%95%9C%EA%B8%80%EC%9D%B8%EC%BD%94%EB%94%A9";
        String decoded = "한글인코딩";
        assertThat(IOUtils.decode(encoded)).isEqualTo(decoded);
    }
}
