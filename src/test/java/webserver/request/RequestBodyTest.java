package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RequestBodyTest extends BaseTest {

    @DisplayName("HttpRequestBody 생성")
    @Test
    void of() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes())));
        RequestStatusLine.of(br);
        RequestHeader requestHeader = RequestHeader.of(br);
        RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());

        assertThat(requestBody.getParameterValue("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getParameterValue("password")).isEqualTo("password");
        assertThat(requestBody.getParameterValue("name")).isEqualTo("박재성");
        assertThat(requestBody.getParameterValue("email")).isEqualTo("javajigi@slipp.net");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestBody.getParameterValue("NotContains"));
    }
}
