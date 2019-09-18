package webserver.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.view.NetworkInput;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    private final String requestInput =
            "GET /index.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";
    private final String requestInputWithQueryString =
            "GET /user/create" +
            "?userId=javajigi&name=pobi HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*";
    private InputStream inputStream;
    private InputStream inputStreamWithQueryString;
    private NetworkInput networkInput;
    private NetworkInput networkInputWithQueryString;

    @BeforeEach
    void setUp() {
        inputStream = new ByteArrayInputStream(requestInput.getBytes());
        inputStreamWithQueryString = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        networkInput = new NetworkInput(inputStream);
        networkInputWithQueryString = new NetworkInput(inputStreamWithQueryString);
    }

    @Test
    @DisplayName("쿼리없는 객체 생성 테스트")
    void create() {
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
    void createWithQueryString() {
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