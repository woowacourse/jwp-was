package model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void request_POST() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        RequestParser requestParser = new RequestParser(in);
        Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getUrl()).isEqualTo("/user/create");
        assertThat(requestParser.getParameter().get("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        RequestParser requestParser = new RequestParser(in);
        Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }
}