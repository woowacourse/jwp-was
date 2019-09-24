package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.io.IOUtils;

import java.io.BufferedReader;
import java.io.StringReader;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        final String DATA = "abcd123";
        final StringReader sr = new StringReader(DATA);
        final BufferedReader br = new BufferedReader(sr);
        logger.debug("deserialize body : {}", IOUtils.readData(br, DATA.length()));
    }
}
