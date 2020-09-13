package webserver.dto;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_LOCATION;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static util.Constants.EMPTY;
import static util.Constants.PROTOCOL;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpStatusCode;

class HttpResponseTest {

    @DisplayName("Protocol이 Null인 경우 NPE 발생")
    @Test
    void of_NullProtocol_ThrownException() {
        assertThatThrownBy(() -> HttpResponse.of(null, HttpStatusCode.OK, new HashMap<>(), EMPTY))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("HttpStatusCode가 Null인 경우 NPE 발생")
    @Test
    void of_NullHttpStatusCode_ThrownException() {
        assertThatThrownBy(() -> HttpResponse.of(PROTOCOL, null, new HashMap<>(), EMPTY))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Headers가 Null인 경우 NPE 발생")
    @Test
    void of_NullHeaders_ThrownException() {
        assertThatThrownBy(() -> HttpResponse.of(PROTOCOL, HttpStatusCode.OK, null, EMPTY))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Body가 Null인 경우 NPE 발생")
    @Test
    void of_NullBody_ThrownException() {
        assertThatThrownBy(() ->
            HttpResponse.of(PROTOCOL, HttpStatusCode.OK, new HashMap<>(), (String) null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("바디가 Empty인 경우 생성자 정상 생성")
    @Test
    void of_BodyIsEmpty_Success() {
        HttpResponse httpResponse
            = HttpResponse.of(PROTOCOL, HttpStatusCode.OK, new HashMap<>(), EMPTY);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(2);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH)).isEqualTo("0");
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE)).isEqualTo(CONTENT_TYPE_TEXT_PLAIN);
        assertThat(httpResponse.getBody()).isEmpty();
    }

    @DisplayName("Body에 값이 들어간 경우도 정상 생성")
    @Test
    void of_ExistsBody_Success() {
        String body = "니나노";
        byte[] expectedBody = body.getBytes(StandardCharsets.UTF_8);
        HttpResponse httpResponse
            = HttpResponse.of(PROTOCOL, HttpStatusCode.OK, new HashMap<>(), body);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(2);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH))
            .isEqualTo(String.valueOf(expectedBody.length));
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo(CONTENT_TYPE_TEXT_PLAIN);
        assertThat(httpResponse.getBody()).isEqualTo(expectedBody);
    }

    @DisplayName("CONTENT_TYPE을 직접 넣는 경우, 직접 넣은 헤더 사용(단 charset 붙여줌)")
    @Test
    void of_InputContentType_SuccessWithContentType() {
        Map<String, String> headers = new HashMap<>();
        String expectedContentType = "text/css";
        headers.put(CONTENT_TYPE, expectedContentType);

        HttpResponse httpResponse
            = HttpResponse.of(PROTOCOL, HttpStatusCode.OK, headers, EMPTY);

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
    void of_InputHeaders_SuccessWithHeaders() {
        Map<String, String> headers = new HashMap<>();
        String expectedLocation = "/index";
        headers.put(CONTENT_LOCATION, expectedLocation);

        HttpResponse httpResponse
            = HttpResponse.of(PROTOCOL, HttpStatusCode.OK, headers, EMPTY);

        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHeaders()).hasSize(3);
        assertThat(httpResponse.getHeaders().get(CONTENT_LENGTH)).isEqualTo("0");
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo(CONTENT_TYPE_TEXT_PLAIN);
        assertThat(httpResponse.getHeaders().get(CONTENT_LOCATION)).isEqualTo(expectedLocation);
        assertThat(httpResponse.getBody()).isEmpty();
    }
}