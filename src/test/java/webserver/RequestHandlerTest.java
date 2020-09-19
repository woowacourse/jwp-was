package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    private RequestHandler requestHandler;

    @BeforeEach
    private void setUp() {
        requestHandler = new RequestHandler(new Socket());
    }

    @DisplayName("/index.html 로 접속했을 때 index.html을 반환한다.")
    @Test
    void indexHandleTest() throws IOException, URISyntaxException {
        // given
        StringBuilder request = new StringBuilder();
        request.append("GET /index.html HTTP/1.1 \n");
        request.append("Host: localhost:8080 \n");
        request.append("Connection: keep-alive \n");
        request.append("Accept: */* \n");

        InputStream inputStream = new ByteArrayInputStream(request.toString().getBytes());

        // when
        byte[] actual = requestHandler.handle(inputStream);

        // then
        byte[] expected = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("매칭되는 location이 없으면 Default 값을 반환한다.")
    @Test
    void defaultHandleTest() {
        // given
        StringBuilder request = new StringBuilder();
        request.append("GET / HTTP/1.1 \n");
        request.append("Host: localhost:8080 \n");
        request.append("Connection: keep-alive \n");
        request.append("Accept: */* \n");

        InputStream inputStream = new ByteArrayInputStream(request.toString().getBytes());

        // when
        byte[] actual = requestHandler.handle(inputStream);

        // then
        byte[] expected = RequestHandler.getDefaultBody();
        assertThat(actual).isEqualTo(expected);
    }
}