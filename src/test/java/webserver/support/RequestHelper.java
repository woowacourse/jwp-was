package webserver.support;

import org.junit.jupiter.api.BeforeEach;
import utils.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RequestHelper {
    protected final String requestStartLineInput = "GET /index.html HTTP/1.1";
    protected final String requestInput =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";
    protected final String requestInputWithQueryString =
            "GET /user/create?userId=javajigi&name=pobi HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";
    private InputStream inputStream;
    private InputStream inputStreamWithQueryString;
    protected IOUtils ioUtils;
    protected IOUtils ioUtilsWithQueryString;

    @BeforeEach
    void setUp() {
        inputStream = new ByteArrayInputStream(requestInput.getBytes());
        inputStreamWithQueryString = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        ioUtils = new IOUtils(inputStream);
        ioUtilsWithQueryString = new IOUtils(inputStreamWithQueryString);
    }
}
