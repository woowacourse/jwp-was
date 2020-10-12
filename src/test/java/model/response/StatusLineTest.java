package model.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;
import model.general.Status;
import model.request.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StatusLineTest {

    @ParameterizedTest
    @DisplayName("StatusLine 생성")
    @MethodSource("provideStatus")
    void create(String filePath, Status status) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);

        assertThat(StatusLine.of(request, status)).isInstanceOf(StatusLine.class);
    }

    private static Stream<Arguments> provideStatus() {
        return Stream.of(
            Arguments.of("src/test/resources/input/get_template_file_request.txt",
                Status.OK),
            Arguments.of("src/test/resources/input/get_static_file_request.txt",
                Status.OK),
            Arguments.of("src/test/resources/input/get_api_request.txt",
                Status.METHOD_NOT_ALLOWED),
            Arguments.of("src/test/resources/input/post_api_request.txt",
                Status.FOUND),
            Arguments.of("src/test/resources/input/put_api_request.txt",
                Status.METHOD_NOT_ALLOWED)
        );
    }

    @ParameterizedTest
    @DisplayName("HttpVersion 확인")
    @MethodSource("provideStatus")
    void getHttpVersion(String filePath, Status status) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        StatusLine statusLine = StatusLine.of(request, status);

        assertThat(statusLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @ParameterizedTest
    @DisplayName("statusCode 확인")
    @MethodSource("provideStatus")
    void getStatusCode(String filePath, Status status) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        StatusLine statusLine = StatusLine.of(request, status);

        assertThat(statusLine.getStatusCode())
            .isEqualTo(String.valueOf(status.getStatusCode()));
    }

    @ParameterizedTest
    @DisplayName("reasonPhrase 확인")
    @MethodSource("provideStatus")
    void getReasonPhrase(String filePath, Status status) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        StatusLine statusLine = StatusLine.of(request, status);

        assertThat(statusLine.getReasonPhrase()).isEqualTo(status.getReasonPhrase());
    }
}
