package webserver.controller.request.header;

import exception.HttpMethodNotFoundException;
import org.junit.jupiter.api.Test;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.MimeType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class HttpRequestTests {
    private String testDirectory = "./src/test/resources/";
    private InputStream inputStream;

    @Test
    void invalid_method() throws IOException {
        inputStream = new FileInputStream(new File(testDirectory + "Http_invalidMethod.txt"));

        assertThrows(HttpMethodNotFoundException.class, () -> {
            new HttpRequest(inputStream);
        });
    }

    @Test
    void get() throws IOException {
        inputStream = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = new HttpRequest(inputStream);

        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
        assertThat(httpRequest.getMimeType()).isEqualTo(MimeType.HTML);
        assertThat(httpRequest.getHeaderFieldValue("Connection")).isEqualTo("keep-alive");
    }

    @Test
    void post() throws IOException {
        inputStream = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest httpRequest = new HttpRequest(inputStream);

        assertThat(httpRequest.getPath()).isEqualTo("/user/create");
        assertThat(httpRequest.getMimeType()).isEqualTo(MimeType.NONE);
        assertThat(httpRequest.getHeaderFieldValue("Connection")).isEqualTo("keep-alive");
        Map<String, String> bodyFields = httpRequest.getBodyFields();
        assertThat(bodyFields.get("userId")).isEqualTo("kangmin46");
        assertThat(bodyFields.get("password")).isEqualTo("password");
        assertThat(bodyFields.get("name")).isEqualTo("kangmin");
    }
}
