package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BodyTest {
    static Stream<Arguments> bodySource() {
        InputStream bodyInputStream = new ByteArrayInputStream("non empty".getBytes());
        InputStreamReader bodyInputStreamReader = new InputStreamReader(bodyInputStream);
        BufferedReader bodyBufferedReader = new BufferedReader(bodyInputStreamReader);

        InputStream emptyInputStream = new ByteArrayInputStream("".getBytes());
        InputStreamReader emptyInputStreamReader = new InputStreamReader(emptyInputStream);
        BufferedReader emptyBufferedReader = new BufferedReader(emptyInputStreamReader);

        return Stream.of(
                Arguments.of(bodyBufferedReader, BodyState.NOT_EMPTY),
                Arguments.of(emptyBufferedReader, BodyState.EMPTY)
        );
    }

    @DisplayName("요청에 대해 생성한 body의 상태 확인")
    @ParameterizedTest
    @MethodSource("bodySource")
    void ofTest(BufferedReader bufferedReader, BodyState bodyState) throws IOException {
        Body body = Body.of(bufferedReader);

        assertThat(body.getState()).isEqualTo(bodyState);
    }
}
