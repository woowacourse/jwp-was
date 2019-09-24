package webserver.request;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriTest {

    @Test
    void create() throws URISyntaxException {
        RequestUri requestUri = new RequestUri("/user/create?userId=id&password=password&name=gyu");
        assertThat(requestUri).isEqualTo(new RequestUri("/user/create?userId=id&password=password&name=gyu"));
    }

    @Test
    void uri() throws URISyntaxException {
        String plainUri = "/user/create?userId=id&password=password&name=gyu";
        URI uri = new URI(plainUri);
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQuery()).isEqualTo("userId=id&password=password&name=gyu");
    }

    @Test
    void uri_no_query() throws URISyntaxException {
        String plainUri = "/user/create";
        URI uri = new URI(plainUri);
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQuery()).isEqualTo(null);
    }
}