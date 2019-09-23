package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBody {
    private static final Logger logger = LoggerFactory.getLogger(RequestBody.class);

    private String body;

    public RequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        createBody(bufferedReader, contentLength);
    }

    private void createBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        body = IOUtils.readData(bufferedReader, contentLength);
        logger.info("request body contents: {}", body);
    }

    public String getBody() {
        return body;
    }
}
