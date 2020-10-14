package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @DisplayName("길이가 0이 아닐때")
    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        assertThat(IOUtils.readData(br, data.length())).isEqualTo(data);
    }

    @DisplayName("길이가 0일때")
    @Test
    public void readDataWhenZeroLength() throws Exception {
        String data = "";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        assertThat(IOUtils.readData(br, data.length())).isEmpty();
    }
}
