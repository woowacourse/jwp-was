package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RequestHeaderTest {
    private static final String GET_REQUEST_HEADER =
            "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";

    private static final String POST_REQUEST_HEADER =
            "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 93\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n";

    private RequestHeader requestLineOfPostMessage;
    private RequestHeader requestLineOfGetMessage;

    @DisplayName("HttpRequestHeader 생성")
    @Test
    void of() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_HEADER.getBytes())));
        requestLineOfPostMessage = RequestHeader.of(br);

        assertThat(requestLineOfPostMessage.getHeaderFieldValue("Host")).isEqualTo("localhost:8080");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue("Connection")).isEqualTo("keep-alive");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue("Content-Length")).isEqualTo("93");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue("Accept")).isEqualTo("*/*");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestLineOfPostMessage.getHeaderFieldValue("NotContains"));
    }

    @DisplayName("contentLength 추출")
    @Test
    void getContentLength() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_HEADER.getBytes())));
        requestLineOfPostMessage = RequestHeader.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_HEADER.getBytes())));
        requestLineOfGetMessage = RequestHeader.of(br);

        assertThat(requestLineOfPostMessage.getContentLength()).isEqualTo(93);
        assertThat(requestLineOfGetMessage.getContentLength()).isEqualTo(0);
    }
}
