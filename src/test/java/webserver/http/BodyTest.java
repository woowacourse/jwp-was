package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BodyTest {
    @DisplayName("요청에 대해 Body 생성")
    @Test
    void ofTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream("this is body".getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Body body = Body.of(bufferedReader, "12");

        assertAll(
                () -> assertThat(body.getState()).isEqualTo(BodyState.NOT_EMPTY),
                () -> assertThat(body.getContent()).isEqualTo("this is body".getBytes()),
                () -> assertThat(body.getLength()).isEqualTo(12)
        );
    }
}
