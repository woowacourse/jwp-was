package http.response;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import common.TestFileIo;
import http.ContentType;

public class ResponseTest {
    @DisplayName("found 테스트")
    @Test
    void responseFound() throws Exception {
        Response response = new Response(TestFileIo.createOutputStream("http_found.txt"));
        response.found("/index.html");
        BufferedReader br = TestFileIo.readBufferedReader("http_Found.txt");

        assertThat(br.readLine()).isEqualTo("HTTP/1.1 302 Found ");
        assertThat(br.readLine()).isEqualTo("Location: /index.html ");
    }

    @DisplayName("ok 테스트")
    @Test
    void responseOk() throws IOException, URISyntaxException {
        Response response = new Response(TestFileIo.createOutputStream("http_ok.txt"));
        response.ok("/index.html", ContentType.HTML.getContentType());
        BufferedReader br = TestFileIo.readBufferedReader("http_ok.txt");

        assertThat(br.readLine()).isEqualTo("HTTP/1.1 200 OK ");
        assertThat(br.readLine()).isEqualTo("Content-Length: 6902 ");
        assertThat(br.readLine()).isEqualTo("Content-Type: text/html;charset=UTF-8 ");
    }
}
