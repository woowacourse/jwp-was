package jwp.was.webserver.handler;

import static jwp.was.util.Constants.CONTENT_TYPE_TEXT_HTML;
import static jwp.was.util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static jwp.was.util.Constants.HEADERS_EMPTY;
import static jwp.was.util.Constants.PARAMETERS_EMPTY;
import static jwp.was.util.Constants.PARAMETERS_FOR_CREATE_USER;
import static jwp.was.util.Constants.PROTOCOL;
import static jwp.was.util.Constants.URL_PATH_API_CREATE_USER;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static jwp.was.webserver.FileNameExtension.API;
import static jwp.was.webserver.FileNameExtension.HTML;
import static jwp.was.webserver.HttpMethod.GET;
import static jwp.was.webserver.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import jwp.was.webapplicationserver.db.DataBaseTest;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.utils.FileIoUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DispenseHandlerTest {

    private DispenseHandler dispenseHandler = new DispenseHandler();

    @AfterEach
    void tearDown() {
        DataBaseTest.clear();
    }

    @DisplayName("파일인 경우, 파일을 찾아옴")
    @Test
    void dispense_File_GetFile() throws IOException, URISyntaxException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                GET,
                URL_PATH_INDEX_HTML,
                PARAMETERS_EMPTY,
                PROTOCOL,
                HEADERS_EMPTY,
                HTML);
            dispenseHandler.dispense(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.OK.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_HTML);

            byte[] body = FileIoUtils
                .loadFileFromClasspath(httpRequest.getUrlPath(), httpRequest.getDirectory());
            assertThat(os.toByteArray()).contains(body);
        }
    }

    @DisplayName("API인 경우, API를 실행한 후 결과를 받아옴")
    @Test
    void dispense_Api_GetApiResult() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                POST,
                URL_PATH_API_CREATE_USER,
                PARAMETERS_FOR_CREATE_USER,
                PROTOCOL,
                HEADERS_EMPTY,
                API);
            dispenseHandler.dispense(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.FOUND.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);
        }
    }
}
