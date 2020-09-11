package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestBodyTest {
    private static final String REQUEST_AFTER_READ_HEADER = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    private static final String REQUEST_CONTENT = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";
    private static final int CONTENT_LENGTH = 93;

    private BufferedReader REQUEST_BUFFERED_READER;

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST_AFTER_READ_HEADER));
    }

    @Test
    public void construct() {
        RequestBody requestBody =
            RequestBody.from(REQUEST_BUFFERED_READER, ContentType.APPLICATION_X_WWW_FORM_URLENCODED, CONTENT_LENGTH);
        assertAll(
            () -> assertThat(requestBody.getContent()).isEqualTo(REQUEST_CONTENT),
            () -> assertThat(requestBody.getContentType()).isEqualTo(ContentType.APPLICATION_X_WWW_FORM_URLENCODED)
        );
    }
}
