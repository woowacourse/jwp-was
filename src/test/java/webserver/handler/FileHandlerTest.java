package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_BOOTSTRAP_MIN_CSS;
import static util.Constants.URL_INDEX_HTML;

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

class FileHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandlerTest.class);

    private final FileHandler fileHandler = new FileHandler();

    @DisplayName("HTML 파일 호출, 성공")
    @Test
    void loadFile_HTML_Return200() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET.name(), URL_INDEX_HTML,
            new HashMap<>(), PROTOCOL, new HashMap<>(), FileNameExtension.from(URL_INDEX_HTML)
        );
        fileHandler.loadFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toString()).contains("Content-Type: text/html");
        assertThat(os.toByteArray()).contains(body);

        LOGGER.debug("response : {}", os);
    }

    @DisplayName("CSS 파일 호출, 성공")
    @Test
    void loadFile_CSS_Return200() throws IOException, URISyntaxException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET.name(), URL_BOOTSTRAP_MIN_CSS,
            new HashMap<>(), PROTOCOL, new HashMap<>(), FileNameExtension.from(
            URL_BOOTSTRAP_MIN_CSS)
        );
        fileHandler.loadFile(os, httpRequest);

        byte[] body = FileIoUtils
            .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());

        assertThat(os.toString()).contains("200 OK");
        assertThat(os.toString()).contains("Content-Type: text/css");
        assertThat(os.toByteArray()).contains(body);

        LOGGER.debug("response : {}", os);
    }

    @DisplayName("Get이 아닌 메서드로 파일 호출, 405 리턴")
    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "DELETE", "XXX"})
    void loadFile_MethodNotGet_Return405(String httpMethod) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(httpMethod, URL_BOOTSTRAP_MIN_CSS,
            new HashMap<>(),
            PROTOCOL, new HashMap<>(), FileNameExtension.from(URL_BOOTSTRAP_MIN_CSS)
        );
        fileHandler.loadFile(os, httpRequest);

        assertThat(os.toString()).contains("405 METHOD NOT ALLOW");
        assertThat(os.toString()).contains("Content-Type: text/plain");

        LOGGER.debug("response : {}", os);
    }
}