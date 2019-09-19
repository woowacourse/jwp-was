package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.support.RequestHelper;
import webserver.view.NetworkInput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest extends RequestHelper {

    @Test
    @DisplayName("쿼리없는 객체 생성 테스트")
    void create() throws IOException {
        InputStream expectedInputStream = new ByteArrayInputStream(requestInput.getBytes());
        NetworkInput expectedNetworkInput = new NetworkInput(expectedInputStream);

        Request request = new Request(this.networkInput);
        Request expected = new Request(expectedNetworkInput);

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getProtocol()).isEqualTo(expected.getProtocol());
        assertThat(request.getRequestFields()).isEqualTo(expected.getRequestFields());
    }

    @Test
    @DisplayName("쿼리가 있는 객체 생성 테스트")
    void createWithQueryString() throws IOException {
        InputStream expectedInputStream = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        NetworkInput expectedNetworkInput = new NetworkInput(expectedInputStream);

        Request request = new Request(this.networkInputWithQueryString);
        Request expected = new Request(expectedNetworkInput);
        QueryParameter queryParameter = request.getQueryParameter();
        QueryParameter expectedQueryParameter = expected.getQueryParameter();

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getProtocol()).isEqualTo(expected.getProtocol());
        assertThat(request.getRequestFields()).isEqualTo(expected.getRequestFields());
        assertThat(queryParameter.getValue("userId")).isEqualTo(expectedQueryParameter.getValue("userId"));
        assertThat(queryParameter.getValue("name")).isEqualTo(expectedQueryParameter.getValue("name"));
    }
}