package http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    private RequestBody requestBody;
    private static final String FORM_DATA = "userId=aiden&password=password&name=aiden&email=aiden@aiden.com HTTP/1.1";

    @BeforeEach
    void setUp() {
        requestBody = RequestBody.of(FORM_DATA.getBytes());
    }

    @Test
    void getFormDataTest() {
        RequestHeader requestHeader = RequestHeader.of(Arrays.asList(
                HttpRequest.CONTENT_TYPE_NAME + ": " + RequestBody.FOM_DATA_TYPE
        ));
        assertThat(requestBody.getFormData(requestHeader)).isEqualTo(FORM_DATA);
    }
}