package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import request.Uri;

class UriTest {

    private Uri getMethodUri = new Uri("/join?id=abcd&password=test");
    private Uri uriNotUsingQueryString = new Uri("/users/me");

    @Test
    void isUsingQueryString() {
        assertThat(getMethodUri.isUsingQueryString()).isTrue();
        assertThat(uriNotUsingQueryString.isUsingQueryString()).isFalse();
    }

    @Test
    void getDataFromGetMethodUri() {
        Map<String, String> dataOfGetMethodUri = new HashMap<>();
        dataOfGetMethodUri.put("id", "abcd");
        dataOfGetMethodUri.put("password", "test");

        assertThat(getMethodUri.getDataFromGetMethodUri()).isEqualTo(dataOfGetMethodUri);
    }

    @Test
    void isPath() {
        assertThat(getMethodUri.isPath("/join")).isTrue();
        assertThat(getMethodUri.isPath("/joinUs")).isFalse();

        assertThat(uriNotUsingQueryString.isPath("/users/me")).isTrue();
        assertThat(uriNotUsingQueryString.isPath("/users/you")).isFalse();
    }

    @Test
    void getPath() {
        assertThat(getMethodUri.getPath()).isEqualTo("/join");
        assertThat(uriNotUsingQueryString.getPath()).isEqualTo("/users/me");
    }
}
