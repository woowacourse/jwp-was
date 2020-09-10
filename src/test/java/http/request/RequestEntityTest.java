package http.request;

import static http.request.RequestHeaderTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestEntityTest {

    private BufferedReader REQUEST_BUFFERED_READER;

    private static final String REQUEST =
        "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final HttpUrl HTTP_URL = HttpUrl.from("/user/create");

    @BeforeEach
    public void setUp() {
        REQUEST_BUFFERED_READER = new BufferedReader(new StringReader(REQUEST));
    }

    @Test
    public void construct() {
        RequestEntity requestEntity = RequestEntity.from(REQUEST_BUFFERED_READER);
        assertAll(
            () -> assertThat(requestEntity.getHttpMethod()).isEqualTo(HttpMethod.POST),
            () -> assertThat(requestEntity.getHttpUrl()).usingRecursiveComparison().isEqualTo(HTTP_URL),
            () -> assertThat(requestEntity.getHttpVersion()).isEqualTo("HTTP/1.1"),
            () -> assertThat(requestEntity.getRequestHeader().getHeaders()).isEqualTo(HEADERS),
            () -> assertThat(requestEntity.getRequestBody().getContent()).isEqualTo("userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net")
        );
    }
}