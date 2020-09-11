package webserver.httpmessages.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class UriTest {

    private Uri getMethodUri = new Uri("/join?id=abcd&password=test");
    private Uri uriNotUsingQueryString = new Uri("/users/me");

    @Test
    void isQueryString() {
        assertThat(getMethodUri.isQueryString()).isTrue();
        assertThat(uriNotUsingQueryString.isQueryString()).isFalse();
    }

    @Test
    void getDataFromGetMethodUri() {
        Map<String, String> dataOfGetMethodUri = new HashMap<>();
        dataOfGetMethodUri.put("id", "abcd");
        dataOfGetMethodUri.put("password", "test");

        assertThat(getMethodUri.getDataFromGetMethodUri()).isEqualTo(dataOfGetMethodUri);
    }

    @Test
    void getUri() {
        assertThat(getMethodUri.getUri()).isEqualTo("/join?id=abcd&password=test");
        assertThat(uriNotUsingQueryString.getUri()).isEqualTo("/users/me");
    }
}