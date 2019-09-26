package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    private static final String FORM_DATA = "userId=aiden&password=password&name=aiden&email=aiden@aiden.com HTTP/1.1";

    private RequestBody requestBody;

    @BeforeEach
    void setUp() {
        requestBody = RequestBody.of(FORM_DATA.getBytes());
    }

    @Test
    @DisplayName("Body의 값 테스트")
    void getByteTest() {
        assertThat(requestBody.getBody()).isEqualTo(FORM_DATA.getBytes());
    }

    @Test
    @DisplayName("form 타입인 경우")
    void successFormDataTest() {
        RequestHeader requestHeader = RequestHeader.of(Arrays.asList(
                HttpRequest.CONTENT_TYPE_NAME + ": " + RequestBody.FOM_DATA_TYPE
        ));
        assertThat(requestBody.getFormData(requestHeader)).isEqualTo(FORM_DATA);
    }

    @Test
    @DisplayName("form 타입이 아닌 경우")
    void failFormDataTest() {
        RequestHeader requestHeader = RequestHeader.of(Arrays.asList(
                HttpRequest.CONTENT_TYPE_NAME + ": " + "text/plain"
        ));
        assertThat(requestBody.getFormData(requestHeader)).isEqualTo(null);
    }
}