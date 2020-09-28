package jwp.was.webserver.handler;

import static jwp.was.webserver.HttpMethod.GET;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.CONTENT_TYPE_TEXT_CSS;
import static util.Constants.CONTENT_TYPE_TEXT_HTML;
import static util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static util.Constants.HEADERS_EMPTY;
import static util.Constants.PARAMETERS_EMPTY;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_PATH_BOOTSTRAP_MIN_CSS;
import static util.Constants.URL_PATH_INDEX_HTML;
import static util.Constants.URL_PATH_NOT_EXISTS_FILE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import jwp.was.webserver.FileNameExtension;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.UrlPath;
import jwp.was.webserver.utils.FileIoUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FileHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandlerTest.class);

    private final FileHandler fileHandler = new FileHandler();

    @DisplayName("HTML 파일 호출, 성공")
    @Test
    void loadFile_HTML_Return200() throws IOException, URISyntaxException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = makeHttpRequest(GET, URL_PATH_INDEX_HTML);

            fileHandler.loadFile(os, httpRequest);

            byte[] body = FileIoUtils
                .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());

            assertThat(os.toString()).contains(HttpStatusCode.OK.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_HTML);
            assertThat(os.toByteArray()).contains(body);

            LOGGER.debug("response : {}", os);
        }
    }

    @DisplayName("CSS 파일 호출, 성공")
    @Test
    void loadFile_CSS_Return200() throws IOException, URISyntaxException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = makeHttpRequest(GET, URL_PATH_BOOTSTRAP_MIN_CSS);

            fileHandler.loadFile(os, httpRequest);

            byte[] body = FileIoUtils
                .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());

            assertThat(os.toString()).contains(HttpStatusCode.OK.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_CSS);
            assertThat(os.toByteArray()).contains(body);

            LOGGER.debug("response : {}", os);
        }
    }

    @DisplayName("Get이 아닌 메서드로 파일 호출, 405 리턴")
    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "DELETE"})
    void loadFile_MethodNotGet_Return405(String httpMethod) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest
                = makeHttpRequest(HttpMethod.from(httpMethod), URL_PATH_BOOTSTRAP_MIN_CSS);
            fileHandler.loadFile(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.METHOD_NOT_ALLOW.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);

            LOGGER.debug("response : {}", os);
        }
    }

    @DisplayName("없는 파일 호출, 찾을 수 없음")
    @Test
    void loadFile_NotExistsFile_Return404() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = makeHttpRequest(GET, URL_PATH_NOT_EXISTS_FILE);
            fileHandler.loadFile(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.NOT_FOUND.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);

            LOGGER.debug("response : {}", os);
        }
    }

    private HttpRequest makeHttpRequest(HttpMethod httpMethod, UrlPath urlPath) {
        return new HttpRequest(
            httpMethod,
            urlPath,
            PARAMETERS_EMPTY,
            PROTOCOL,
            HEADERS_EMPTY,
            FileNameExtension.from(urlPath)
        );
    }
}
