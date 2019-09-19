package webserver.view;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import webserver.support.RequestHelper;

import java.io.StringReader;

import static org.slf4j.LoggerFactory.getLogger;

class NetworkInputTest extends RequestHelper {
    private static final Logger LOG = getLogger(NetworkInputTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);

        LOG.debug("parse body : {}", networkInput.readBody(data.length()));
    }
}