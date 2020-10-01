package http.request;

import static http.HttpHeaderTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestEntityTest {

    private BufferedReader REQUEST_BUFFERED_READER_WITH_BODY;
    private BufferedReader REQUEST_BUFFERED_READER_WITHOUT_BODY;

    private static final String REQUEST_WITH_BODY =
        "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final String REQUEST_WITHOUT_BODY =
        "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";

    private static final Map<String, String> HEADERS2 = new HashMap<String, String>() {{
        put("Host", "localhost:8080");
        put("Connection", "keep-alive");
        put("Accept", "*/*");
    }};

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER_WITH_BODY = new BufferedReader(new StringReader(REQUEST_WITH_BODY));
        REQUEST_BUFFERED_READER_WITHOUT_BODY = new BufferedReader(new StringReader(REQUEST_WITHOUT_BODY));
    }

    @Test
    public void constructWithBody() {
        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER_WITH_BODY);
        assertAll(
            () -> assertThat(requestEntity.getHttpMethod()).isEqualTo(HttpMethod.POST),
            () -> assertThat(requestEntity.getHttpUrl()).usingRecursiveComparison().isEqualTo(HttpUrl.from("/user/create")),
            () -> assertThat(requestEntity.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(requestEntity.getHttpHeader().getHeaders()).isEqualTo(HEADERS),
            () -> assertThat(requestEntity.getHttpBody().getContent()).isEqualTo("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net")
        );
    }

    @Test
    public void constructWithoutBody() {
        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER_WITHOUT_BODY);
        assertAll(
            () -> assertThat(requestEntity.getHttpMethod()).isEqualTo(HttpMethod.GET),
            () -> assertThat(requestEntity.getHttpUrl()).usingRecursiveComparison().isEqualTo(HttpUrl.from("/index.html")),
            () -> assertThat(requestEntity.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(requestEntity.getHttpHeader().getHeaders()).isEqualTo(HEADERS2),
            () -> assertThat(requestEntity.getHttpBody()).isNull()
        );
    }
}
