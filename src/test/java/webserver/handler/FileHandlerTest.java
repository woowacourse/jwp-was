package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.FileNameExtension;
import webserver.HttpMethod;
import webserver.dto.HttpRequest;
import webserver.utils.FileIoUtils;
import webserver.utils.FileIoUtilsTest;

class FileHandlerTest {

    private static final Logger logger = LoggerFactory.getLogger(FileIoUtilsTest.class);
    private static final String HTML_URL_PATH = "/index.html";
    private static final String CSS_URL_PATH = "/css/bootstrap.min.css";
    private static final String PROTOCOL = "http/1.1";

    @DisplayName("HTML 파일 호출, 성공")
    @Test
    void loadFile_HTML_Return200() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET.name(), HTML_URL_PATH,
            new HashMap<>(),
            PROTOCOL, new HashMap<>(), FileNameExtension.from(HTML_URL_PATH)
        );
        FileHandler.loadFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toString()).contains("Content-Type: text/html");
        assertThat(os.toByteArray()).contains(body);

        logger.debug("response : {}", os);
    }

    @DisplayName("CSS 파일 호출, 성공")
    @Test
    void loadFile_CSS_Return200() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET.name(), CSS_URL_PATH,
            new HashMap<>(),
            PROTOCOL, new HashMap<>(), FileNameExtension.from(CSS_URL_PATH)
        );
        FileHandler.loadFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toString()).contains("Content-Type: text/css");
        assertThat(os.toByteArray()).contains(body);

        logger.debug("response : {}", os);
    }

    @DisplayName("Get이 아닌 메서드로 파일 호출, 405 리턴")
    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "DELETE", "XXX"})
    void loadFile_MethodNotGet_Return405(String httpMethod) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(httpMethod, CSS_URL_PATH, new HashMap<>(),
            PROTOCOL, new HashMap<>(), FileNameExtension.from(CSS_URL_PATH)
        );
        FileHandler.loadFile(os, httpRequest);

        assertThat(os.toString()).contains("405 METHOD NOT ALLOW");
        assertThat(os.toString()).contains("Content-Type: text/plain");

        logger.debug("response : {}", os);
    }
}