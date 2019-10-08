package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.NetworkIOUtils;

import java.io.BufferedReader;
import java.io.StringReader;

public class NetworkIOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(NetworkIOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        final String DATA = "abcd123";
        final StringReader sr = new StringReader(DATA);
        final BufferedReader br = new BufferedReader(sr);
        logger.debug("deserialize body : {}", NetworkIOUtils.readData(br, DATA.length()));
    }
}
