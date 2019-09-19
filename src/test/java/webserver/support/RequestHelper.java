package webserver.support;

import org.junit.jupiter.api.BeforeEach;
import webserver.view.NetworkInput;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RequestHelper {
    protected final String requestInput =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";
    protected final String requestInputWithQueryString =
            "GET /user/create" +
                    "?userId=javajigi&name=pobi HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";
    private InputStream inputStream;
    private InputStream inputStreamWithQueryString;
    protected NetworkInput networkInput;
    protected NetworkInput networkInputWithQueryString;

    @BeforeEach
    void setUp() {
        inputStream = new ByteArrayInputStream(requestInput.getBytes());
        inputStreamWithQueryString = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        networkInput = new NetworkInput(inputStream);
        networkInputWithQueryString = new NetworkInput(inputStreamWithQueryString);
    }
}
