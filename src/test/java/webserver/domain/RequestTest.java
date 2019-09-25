package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.support.RequestHelper;
import webserver.view.NetworkInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest extends RequestHelper {

    @Test
    @DisplayName("쿼리없는 객체 생성 테스트")
    void create() throws IOException, URISyntaxException {
        final InputStream expectedInputStream = new ByteArrayInputStream(requestInput.getBytes());
        final NetworkInput expectedNetworkInput = new NetworkInput(expectedInputStream);

        final Request request = new Request(this.networkInput);
        final Request expected = new Request(expectedNetworkInput);

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getProtocol()).isEqualTo(expected.getProtocol());
        assertThat(request.getHeader()).isEqualTo(expected.getHeader());
    }

    @Test
    @DisplayName("쿼리가 있는 객체 생성 테스트")
    void createWithQueryString() throws IOException, URISyntaxException {
        final InputStream expectedInputStream = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        final NetworkInput expectedNetworkInput = new NetworkInput(expectedInputStream);

        final Request request = new Request(this.networkInputWithQueryString);
        final Request expected = new Request(expectedNetworkInput);
        final Map<String, String> queryParameter = request.getQueryParameters();
        final Map<String, String> expectedQueryParameter = expected.getQueryParameters();

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getProtocol()).isEqualTo(expected.getProtocol());
        assertThat(request.getHeader()).isEqualTo(expected.getHeader());
        assertThat(queryParameter.get("userId")).isEqualTo(expectedQueryParameter.get("userId"));
        assertThat(queryParameter.get("name")).isEqualTo(expectedQueryParameter.get("name"));
    }
}