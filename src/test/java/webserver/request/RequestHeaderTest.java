package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static webserver.support.ConStants.*;

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

    private static final String HEAD_FIELD_CONNECTION = "Connection";
    private static final String HEADER_FIELD_HOST = "Host";

    @DisplayName("HttpRequestHeader 생성")
    @Test
    void of() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_HEADER.getBytes())));
        RequestHeader requestLineOfPostMessage = RequestHeader.of(br);

        assertThat(requestLineOfPostMessage.getHeaderFieldValue(HEADER_FIELD_HOST)).isEqualTo("localhost:8080");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue(HEAD_FIELD_CONNECTION)).isEqualTo("keep-alive");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue(HEADER_FIELD_CONTENT_LENGTH)).isEqualTo("93");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue(HEADER_FIELD_CONTENT_TYPE)).isEqualTo("application/x-www-form-urlencoded");
        assertThat(requestLineOfPostMessage.getHeaderFieldValue(HEADER_FIELD_ACCEPT)).isEqualTo("*/*");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestLineOfPostMessage.getHeaderFieldValue("NotContains"));
    }

    @DisplayName("contentLength 추출")
    @Test
    void getContentLength() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_HEADER.getBytes())));
        RequestHeader requestLineOfPostMessage = RequestHeader.of(br);
        br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_HEADER.getBytes())));
        RequestHeader requestLineOfGetMessage = RequestHeader.of(br);

        assertThat(requestLineOfPostMessage.getContentLength()).isEqualTo(93);
        assertThat(requestLineOfGetMessage.getContentLength()).isEqualTo(0);
    }
}
