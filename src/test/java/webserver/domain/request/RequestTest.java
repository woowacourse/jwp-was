package webserver.domain.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.IOUtils;
import webserver.support.RequestHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest extends RequestHelper {

    @Test
    @DisplayName("쿼리없는 객체 생성 테스트")
    void create() throws IOException {
        InputStream expectedInputStream = new ByteArrayInputStream(requestInput.getBytes());
        IOUtils expectedNetworkInput = new IOUtils(expectedInputStream);

        Request request = new Request(this.ioUtils);
        Request expected = new Request(expectedNetworkInput);

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getHttpVersion()).isEqualTo(expected.getHttpVersion());
        assertThat(request.getHeader().getRequestFields()).isEqualTo(expected.getHeader().getRequestFields());
        assertThat(request.getBody().getQueries()).isEqualTo(expected.getBody().getQueries());
    }

    @Test
    @DisplayName("쿼리가 있는 객체 생성 테스트")
    void createWithQueryString() throws IOException {
        InputStream expectedInputStream = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        IOUtils expectedNetworkInput = new IOUtils(expectedInputStream);

        Request request = new Request(this.ioUtilsWithQueryString);
        Request expected = new Request(expectedNetworkInput);

        assertThat(request.getHttpMethod()).isEqualTo(expected.getHttpMethod());
        assertThat(request.getPath()).isEqualTo(expected.getPath());
        assertThat(request.getHttpVersion()).isEqualTo(expected.getHttpVersion());
        assertThat(request.getHeader().getRequestFields()).isEqualTo(expected.getHeader().getRequestFields());
        assertThat(request.getBody().getQueries()).isEqualTo(expected.getBody().getQueries());
    }
}