package webserver.dto;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_LOCATION;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpStatusCode;

class HttpResponseTest {

    private static final String EMPTY = "";
    private static final String PROTOCOL = "HTTP/1.1";

    @DisplayName("Protocol이 Null인 경우 NPE 발생")
    @Test
    void constructor_NullProtocol_ThrownException() {
        assertThatThrownBy(() -> new HttpResponse(null, HttpStatusCode.OK, new HashMap<>(), EMPTY))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("HttpStatusCode가 Null인 경우 NPE 발생")
    @Test
    void constructor_NullHttpStatusCode_ThrownException() {
        assertThatThrownBy(() -> new HttpResponse(PROTOCOL, null, new HashMap<>(), EMPTY))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Headers가 Null인 경우 NPE 발생")
    @Test
    void constructor_NullHeaders_ThrownException() {
        assertThatThrownBy(() -> new HttpResponse(PROTOCOL, HttpStatusCode.OK, null, EMPTY))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Body가 Null인 경우 NPE 발생")
    @Test
    void constructor_NullBody_ThrownException() {
        assertThatThrownBy(() ->
            new HttpResponse(PROTOCOL, HttpStatusCode.OK, new HashMap<>(), (byte[]) null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("바디가 Empty인 경우 생성자 정상 생성")
    @Test
    void constructor_BodyIsEmpty_Success() {
        HttpResponse httpResponse
            = new HttpResponse(PROTOCOL, HttpStatusCode.OK, new HashMap<>(), EMPTY);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(2);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH)).isEqualTo("0");
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE)).contains("plain");
        assertThat(httpResponse.getBody()).isEmpty();
    }

    @DisplayName("Body에 값이 들어간 경우도 정상 생성")
    @Test
    void constructor_ExistsBody_Success() {
        String body = "니나노";
        byte[] expectedBody = body.getBytes(StandardCharsets.UTF_8);
        HttpResponse httpResponse
            = new HttpResponse(PROTOCOL, HttpStatusCode.OK, new HashMap<>(), body);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(2);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH))
            .isEqualTo(String.valueOf(expectedBody.length));
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo("text/plain;charset=utf-8");
        assertThat(httpResponse.getBody()).isEqualTo(expectedBody);
    }

    @DisplayName("CONTENT_TYPE을 직접 넣는 경우, 직접 넣은 헤더 사용(단 charset 붙여줌)")
    @Test
    void constructor_InputContentType_SuccessWithContentType() {
        Map<String, String> headers = new HashMap<>();
        String expectedContentType = "text/css";
        headers.put(CONTENT_TYPE, expectedContentType);

        HttpResponse httpResponse
            = new HttpResponse(PROTOCOL, HttpStatusCode.OK, headers, EMPTY);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(2);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH)).isEqualTo("0");
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo(expectedContentType + ";charset=utf-8");
        assertThat(httpResponse.getBody()).isEmpty();
    }

    @DisplayName("헤더를 직접 넣는 경우, 헤더 포함 생성")
    @Test
    void constructor_InputHeaders_SuccessWithHeaders() {
        Map<String, String> headers = new HashMap<>();
        String expectedLocation = "/index";
        headers.put(CONTENT_LOCATION, expectedLocation);

        HttpResponse httpResponse
            = new HttpResponse(PROTOCOL, HttpStatusCode.OK, headers, EMPTY);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(3);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH)).isEqualTo("0");
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo("text/plain;charset=utf-8");
        assertThat(httpResponse.getHeaders().get(CONTENT_LOCATION)).isEqualTo(expectedLocation);
        assertThat(httpResponse.getBody()).isEmpty();
    }
}