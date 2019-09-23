package webserver.request.requestline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestUriTest {

    private RequestUri requestUri;
    private RequestUri requestUriWithQueryString;

    @BeforeEach
    void setUp() {
        String uri = "/index.html";
        requestUri = new RequestUri(uri);

        String uriWithQueryString = "/user/create?name=nick&password=1234";
        requestUriWithQueryString = new RequestUri(uriWithQueryString);
    }

    @Test
    void findExtension() {
        assertThat(requestUri.findExtension()).isEqualByComparingTo(RequestUriExtension.HTML);
    }

    @Test
    void findExtension_none() {
        assertThat(requestUriWithQueryString.findExtension()).isEqualByComparingTo(RequestUriExtension.NONE);
    }

    @Test
    void findQueryParams() {
        QueryParams queryParams = requestUriWithQueryString.findQueryParams();
        assertThat(queryParams.findParam("name")).isEqualTo("nick");
        assertThat(queryParams.findParam("password")).isEqualTo("1234");
    }

    @Test
    void findQueryParams_empty() {
        QueryParams queryParams = requestUri.findQueryParams();
        assertThat(queryParams.isEmpty()).isTrue();
    }
}