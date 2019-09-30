package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.support.RequestHelper;
import webserver.view.NetworkInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

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
        final QueryParameter queryParameter = request.getQueryParameters();
        final QueryParameter expectedQueryParameter = expected.getQueryParameters();

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getProtocol()).isEqualTo(expected.getProtocol());
        assertThat(request.getHeader()).isEqualTo(expected.getHeader());
        assertThat(queryParameter.getValue("userId")).isEqualTo(expectedQueryParameter.getValue("userId"));
        assertThat(queryParameter.getValue("name")).isEqualTo(expectedQueryParameter.getValue("name"));
    }
}