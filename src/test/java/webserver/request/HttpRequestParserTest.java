package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.requestline.HttpMethod;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.RequestHeaderFieldKeys.*;

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
        assertThat(httpRequest.findHeaderField(END_POINT)).isEqualTo("991d27d9-b24f-44b0-b373-1f05c2bacd89");
        assertThat(httpRequest.findHeaderField(JSESSIONID)).isEqualTo("C46C9A085D907B5D12F7568E0A692145");
        assertThat(httpRequest.findHeaderField(LOGINED)).isEqualTo("true");
        assertThat(httpRequest.findHeaderField(USER_AGENT)).isEqualTo("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
    }
}