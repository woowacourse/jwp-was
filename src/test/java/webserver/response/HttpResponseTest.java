package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpResponseTest {

    private static final String TEST_RESPONSE_DIRECTORY = "./src/test/resources/response/";

    @DisplayName("HttpResponse Forward - non Static")
    @Test
    public void responseForward_HTML() throws Exception {
        String fileName = "Http_Response_Forward_HTML.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));

        response.forward("/index.html");
        String responseText = convertText(createBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 200 Ok");
        assertThat(responseText).contains("Content-Length: ");
        assertThat(responseText).contains("</body></html>");
    }

    @DisplayName("HttpResponse Forward - Static")
    @Test
    public void responseForward_CSS() throws Exception {
        String fileName = "Http_Response_Forward_CSS.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));

        response.forward("/css/styles.css");
        String responseText = convertText(createBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 200 Ok");
    }

    @DisplayName("HttpResponse Redirect")
    @Test
    public void responseRedirect() throws Exception {
        // Http_Redirect.txt 결과는 응답 headere에 Location 정보가 /index.html로 포함되어 있어야 한다.
        String fileName = "Http_Response_Redirect.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));

        response.sendRedirect("/index.html");
        String responseText = convertText(createBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 302 Found");
        assertThat(responseText).contains("Location: /index.html");
        assertThat(responseText).contains("</body></html>");
    }

    @DisplayName("HttpResponse Cookies")
    @Test
    public void responseCookies() throws Exception {
        // Http_Cookie.txt 결과는 응답 header에 Set-Cookie 값으로 logined=true 값이 포함되어 있어야 한다.
        String fileName = "Http_Response_Cookie.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));
        response.addHeader("Set-Cookie", "logined=true");

        response.sendRedirect("/index.html");
        String responseText = convertText(createBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 302 Found");
        assertThat(responseText).contains("Location: /index.html");
        assertThat(responseText).contains("Set-Cookie: logined=true");
        assertThat(responseText).contains("</body></html>");
    }

    @DisplayName("HttpResponse MethodNotAllowed")
    @Test
    public void responseMethodNotAllowed() throws Exception {
        String fileName = "Http_Response_MethodNotAllowed.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));

        response.methodNotAllowed("/error.html");
        String responseText = convertText(createBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 405 Method Not Allowed");
        assertThat(responseText).contains("</body></html>");
    }

    @DisplayName("HttpResponse NotImplemented")
    @Test
    public void responseNotImplemented() throws Exception {
        String fileName = "Http_Response_NotImplemented.txt";
        HttpResponse response = new HttpResponse(createOutputStream(fileName));

        response.notImplemented("/error.html");
        String responseText = convertText(createBufferedReader(fileName));

        assertThat(responseText).contains("HTTP/1.1 501 Not Implemented");
        assertThat(responseText).contains("</body></html>");
    }

    private String convertText(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while (!Objects.isNull(line = bufferedReader.readLine())) {
            sb.append(line);
        }
        return sb.toString();
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(TEST_RESPONSE_DIRECTORY + filename));
    }

    private BufferedReader createBufferedReader(String filename) throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(TEST_RESPONSE_DIRECTORY + filename));
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
