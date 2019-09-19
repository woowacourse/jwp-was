package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.requestline.HttpMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParserTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void parseHttpRequest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = HttpRequestParser.parseHttpRequest(br);

        assertThat(httpRequest.findMethod()).isEqualByComparingTo(HttpMethod.GET);
        assertThat(httpRequest.findUri()).isEqualTo("/user/create?userId=javajigi&password=password&name=JaeSung");
        assertThat(httpRequest.findHeaderField("Host")).isEqualTo("localhost:8080");
    }
}