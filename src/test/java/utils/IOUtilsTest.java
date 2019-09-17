package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;

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
    public void readStrings() throws Exception {
        String expected = "Request URL: http://localhost:8080/\n" +
                "Request Method: GET\n" +
                "Status Code: 200 OK\n" +
                "Remote Address: [::1]:8080\n" +
                "Referrer Policy: no-referrer-when-downgrade\n";
        StringReader sr = new StringReader(expected);
        BufferedReader br = new BufferedReader(sr);

        assertThat(IOUtils.parseData(br)).isEqualTo(expected);
    }
}
