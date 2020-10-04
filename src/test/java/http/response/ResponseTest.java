package http.response;

import http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {
    private String testDirectory = "./src/test/resources/";

    @DisplayName("found 테스트")
    @Test
    void responseFound() throws Exception {
        Response response = new Response(createOutputStream("Http_Found.txt"));
        response.found("/index.html");
        BufferedReader br = readBufferedReader("Http_Found.txt");

        assertThat(br.readLine()).isEqualTo("HTTP/1.1 302 Found ");
        assertThat(br.readLine()).isEqualTo("Location: /index.html ");
    }

    @DisplayName("ok 테스트")
    @Test
    void responseOk() throws IOException, URISyntaxException {
        Response response = new Response(createOutputStream("Http_Ok.txt"));
        response.ok("/index.html", ContentType.HTML.getContentType());
        BufferedReader br = readBufferedReader("Http_Ok.txt");

        assertThat(br.readLine()).isEqualTo("HTTP/1.1 200 OK ");
        assertThat(br.readLine()).isEqualTo("Content-Length: 7049 ");
        assertThat(br.readLine()).isEqualTo("Content-Type: text/html;charset=UTF-8 ");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

    private BufferedReader readBufferedReader(String filename) throws FileNotFoundException {
        File file = new File(testDirectory + filename);
        InputStream in = new FileInputStream(file);
        return new BufferedReader(new InputStreamReader(in));
    }
}