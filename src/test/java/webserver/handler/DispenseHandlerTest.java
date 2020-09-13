package webserver.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.CONTENT_TYPE_TEXT_HTML;
import static util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_API_CREATE_USER;
import static util.Constants.URL_INDEX_HTML;
import static util.Constants.USER_EMAIL;
import static util.Constants.USER_ID;
import static util.Constants.USER_NAME;
import static util.Constants.USER_PASSWORD;

import db.DataBase;
import db.DataBaseTest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.FileNameExtension;
import webserver.HttpMethod;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.utils.FileIoUtils;

class DispenseHandlerTest {

    private DispenseHandler dispenseHandler = new DispenseHandler();
    private DataBase dataBase = new DataBase();

    @AfterEach
    void tearDown() {
        DataBaseTest.clear();
    }

    @DisplayName("파일인 경우, 파일을 찾아옴")
    @Test
    void dispense_File_GetFile() throws IOException, URISyntaxException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(HttpMethod.GET.name(), URL_INDEX_HTML,
                new HashMap<>(), PROTOCOL, new HashMap<>(), FileNameExtension.HTML);
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
            Map<String, String> parameter = new HashMap<String, String>() {{
                put(USER_ID, USER_ID);
                put(USER_EMAIL, USER_EMAIL);
                put(USER_PASSWORD, USER_PASSWORD);
                put(USER_NAME, USER_NAME);
            }};
            HttpRequest httpRequest = new HttpRequest(HttpMethod.POST.name(), URL_API_CREATE_USER,
                parameter, PROTOCOL, new HashMap<>(), FileNameExtension.API);
            dispenseHandler.dispense(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.FOUND.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);
        }
    }
}