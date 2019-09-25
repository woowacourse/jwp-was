package webserver.request;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void uri_static() throws URISyntaxException {
        String plainUri = "/index.html";
        URI uri = new URI(plainUri);
        assertThat(uri.getPath()).isEqualTo("/index.html");
        assertThat(uri.getQuery()).isEqualTo(null);
    }

    @Test
    void uri_isFile_true() throws URISyntaxException {
        RequestUri fileRequestUri = new RequestUri("/index.html");
        assertTrue(fileRequestUri.isFile());
    }

    @Test
    void uri_isFile_false() throws URISyntaxException {
        RequestUri fileRequestUri = new RequestUri("/user/create");
        assertFalse(fileRequestUri.isFile());
    }

    @Test
    void uri_no_query() throws URISyntaxException {
        String plainUri = "/user/create";
        URI uri = new URI(plainUri);
        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.getQuery()).isEqualTo(null);
    }
}