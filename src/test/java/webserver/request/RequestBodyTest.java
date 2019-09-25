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
        RequestLine.of(br);
        RequestHeader.of(br);
        RequestBody requestBody = RequestBody.of(br, 93);

        assertThat(requestBody.get("userId")).isEqualTo("javajigi");
        assertThat(requestBody.get("password")).isEqualTo("password");
        assertThat(requestBody.get("name")).isEqualTo("박재성");
        assertThat(requestBody.get("email")).isEqualTo("javajigi@slipp.net");
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> requestBody.get("NotContains"));
    }
}
