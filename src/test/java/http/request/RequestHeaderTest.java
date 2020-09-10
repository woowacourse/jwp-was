package http.request;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RequestHeaderTest {

    private BufferedReader REQUEST_BUFFERED_READER;
    private RequestHeader REQUEST_HEADER;

    private static final String REQUEST =
        "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    public static final Map<String, String> HEADERS = new HashMap<String, String>() {{
        put("Host", "localhost:8080");
        put("Connection", "keep-alive");
        put("Content-Length", "93");
        put("Content-Type", "application/x-www-form-urlencoded");
        put("Accept", "*/*");
    }};

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST));
        REQUEST_HEADER = RequestHeader.from(REQUEST_BUFFERED_READER);
    }

    @Test
    public void construct() {
        assertThat(REQUEST_HEADER.getHeaders()).isEqualTo(HEADERS);
    }

    @ParameterizedTest
    @CsvSource({"Host,localhost:8080", "Accept,*/*", "notExist,"})
    public void findOrEmpty(String input, String output) {
        String value = REQUEST_HEADER.findOrEmpty(input);
        String expected = nullToEmpty(output);
        assertThat(value).isEqualTo(expected);
    }

    private String nullToEmpty(String input) {
        if (input == null) {
            return "";
        }
        return input;
    }
}