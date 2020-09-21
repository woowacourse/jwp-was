package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import webserver.request.Request;

public class RequestTest {

    private String testDirectory = "./src/test/resources/";

    private Request generateRequest(String request) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + request));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        List<String> lines = new ArrayList<>();

        while (!"".equals(line = br.readLine()) && line != null) {
            lines.add(line);
        }
        return new Request(lines, br);
    }

    @Test
    public void requestTest_GET() throws Exception {
        Request request = generateRequest("http_GET.txt");

        assertThat(request.getMethod()).isEqualTo(GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    public void requestTest_POST() throws Exception {
        Request request = generateRequest("http_POST.txt");

        assertThat(request.getMethod()).isEqualTo(POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }
}
