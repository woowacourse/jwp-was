package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HttpRequestTest {
    private static final String testDirectory = "./src/test/resources";

    private static Stream<Arguments> providePathAndResult() {
        return Stream.of(
                Arguments.of("/user/create", true),
                Arguments.of("/users", false)
        );
    }

    @DisplayName("from: body가 없는 get요청을 받아 인스턴스를 생성한다.")
    @Test
    void from_GetRequestWithoutBody_Instantiation() throws IOException {
        // given
        BufferedReader bufferedReader = createBufferedReader("/http_get.txt");

        // when
        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        // then
        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isNotNull(),
                () -> assertThat(httpRequest.getHttpHeaders()).isNotNull(),
                () -> assertThat(httpRequest.getRequestBody()).isNull()
        );
    }

    @DisplayName("from: body가 있는 post요청을 받아 인스턴스를 생성한다.")
    @Test
    void from_PostRequestWithBody_Instantiation() throws IOException {
        // given
        BufferedReader bufferedReader = createBufferedReader("/http_post.txt");

        // when
        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        // then
        assertAll(
                () -> assertThat(httpRequest.equalsPath("/user/create")).isTrue(),
                () -> assertThat(httpRequest.equalsMethod(HttpMethod.POST)).isTrue(),
                () -> assertThat(httpRequest.getRequestLine().getVersion()).isEqualTo("HTTP/1.1"),
                () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("46"),
                () -> assertThat(httpRequest.getBodyValue("userId")).isEqualTo("javajigi"),
                () -> assertThat(httpRequest.getBodyValue("name")).isEqualTo("JaeSung")
        );
    }

    @DisplayName("equalsPath: 입력받은 path와 동일한 path를 가지는지 확인한다.")
    @MethodSource("providePathAndResult")
    @ParameterizedTest
    void equalsPath(final String path, final boolean expect) throws IOException {
        // when
        BufferedReader bufferedReader = createBufferedReader("/http_get.txt");
        HttpRequest httpRequest = HttpRequest.from(bufferedReader);

        // when
        boolean actual = httpRequest.equalsPath(path);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private BufferedReader createBufferedReader(final String fileName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + fileName));
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(inputStreamReader);
    }
}
