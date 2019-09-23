package http.request;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpRequest httpRequest;

    @Test
    public void createTest() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        RequestHandler requestHandler = new RequestHandler(br);
        httpRequest = requestHandler.create();

        assertThat(httpRequest.getUrl()).isEqualTo("http://localhost:8080/user/create");
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequest.getParameter("userId")).isEqualTo("aiden");
    }
}