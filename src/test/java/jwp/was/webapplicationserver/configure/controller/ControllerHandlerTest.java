package jwp.was.webapplicationserver.configure.controller;

import static com.google.common.net.HttpHeaders.COOKIE;
import static jwp.was.util.Constants.CONTENT_TYPE_TEXT_HTML;
import static jwp.was.util.Constants.CONTENT_TYPE_TEXT_PLAIN;
import static jwp.was.util.Constants.HEADERS_EMPTY;
import static jwp.was.util.Constants.HTTP_VERSION;
import static jwp.was.util.Constants.PARAMETERS_EMPTY;
import static jwp.was.util.Constants.PARAMETERS_FOR_CREATE_USER;
import static jwp.was.util.Constants.SET_COOKIE_SESSION_ID_KEY;
import static jwp.was.util.Constants.URL_PATH_API_CREATE_USER;
import static jwp.was.util.Constants.URL_PATH_LOGIN_HTML;
import static jwp.was.util.Constants.URL_PATH_NOT_EXISTS_FILE;
import static jwp.was.util.Constants.URL_PATH_PAGE_API_USER_LIST;
import static jwp.was.util.Constants.USER_EMAIL;
import static jwp.was.util.Constants.USER_ID;
import static jwp.was.util.Constants.USER_NAME;
import static jwp.was.util.Constants.USER_PASSWORD;
import static jwp.was.webapplicationserver.configure.security.WithLoginConfigure.ATTRIBUTE_KEY_USER;
import static jwp.was.webserver.HttpMethod.CONNECT;
import static jwp.was.webserver.HttpMethod.GET;
import static jwp.was.webserver.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jwp.was.webapplicationserver.configure.session.HttpSession;
import jwp.was.webapplicationserver.configure.session.HttpSessionImpl;
import jwp.was.webapplicationserver.configure.session.HttpSessions;
import jwp.was.webapplicationserver.db.DataBaseTest;
import jwp.was.webapplicationserver.model.User;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.Headers;
import jwp.was.webserver.dto.HttpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerHandlerTest {

    private final ControllerHandler controllerHandler = new ControllerHandler();

    @AfterEach
    void tearDown() {
        DataBaseTest.clear();
    }

    @DisplayName("유저 생성 성공, 302 반환")
    @Test
    void handleAPI_UserCreate_Return302() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                POST,
                URL_PATH_API_CREATE_USER,
                PARAMETERS_FOR_CREATE_USER,
                HTTP_VERSION,
                HEADERS_EMPTY
            );
            controllerHandler.handleAPI(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.FOUND.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);
        }
    }

    @DisplayName("지원하지 않는 메서드, 405 반환")
    @Test
    void handleAPI_NotAllowMethod_Return405() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                CONNECT,
                URL_PATH_API_CREATE_USER,
                PARAMETERS_FOR_CREATE_USER,
                HTTP_VERSION,
                HEADERS_EMPTY
            );
            controllerHandler.handleAPI(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.METHOD_NOT_ALLOW.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);
        }
    }

    @DisplayName("없는 API, 404 반환")
    @Test
    void handleAPI_NotExistsAPI_Return404() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                POST,
                URL_PATH_NOT_EXISTS_FILE,
                PARAMETERS_FOR_CREATE_USER,
                HTTP_VERSION,
                HEADERS_EMPTY
            );
            controllerHandler.handleAPI(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.NOT_FOUND.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);
        }
    }

    @DisplayName("잘못된 parameter, 400 반환")
    @Test
    void handleAPI_WrongParameters_Return400() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                POST,
                URL_PATH_API_CREATE_USER,
                PARAMETERS_EMPTY,
                HTTP_VERSION,
                HEADERS_EMPTY
            );
            controllerHandler.handleAPI(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.BAD_REQUEST.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_PLAIN);
        }
    }

    @DisplayName("UserList 조회 - 302 반환, Cookie 없음")
    @Test
    void handleAPI_GetUserListWithoutCookie_Return302() throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                GET,
                URL_PATH_PAGE_API_USER_LIST,
                PARAMETERS_EMPTY,
                HTTP_VERSION,
                HEADERS_EMPTY
            );
            controllerHandler.handleAPI(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.FOUND.getCodeAndMessage());
            assertThat(os.toString()).contains(URL_PATH_LOGIN_HTML);
        }
    }

    @DisplayName("UserList 조회 - 200 반환, Cookie 포함")
    @Test
    void handleAPI_GetUserListWithCookie_Return200() throws IOException {
        HttpSessions httpSessions = HttpSessions.getInstance();
        HttpSession httpSession = new HttpSessionImpl();
        User user = new User(USER_ID, USER_PASSWORD, USER_NAME, USER_EMAIL);
        httpSession.setAttribute(ATTRIBUTE_KEY_USER, user);
        httpSessions.saveSession(httpSession);

        Map<String, String> headers = new HashMap<>();
        headers.put(COOKIE, SET_COOKIE_SESSION_ID_KEY + httpSession.getId());
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(
                GET,
                URL_PATH_PAGE_API_USER_LIST,
                PARAMETERS_EMPTY,
                HTTP_VERSION,
                new Headers(headers)
            );
            controllerHandler.handleAPI(os, httpRequest);

            assertThat(os.toString()).contains(HttpStatusCode.OK.getCodeAndMessage());
            assertThat(os.toString()).contains(CONTENT_TYPE_TEXT_HTML);
        }

        httpSessions.removeSession(httpSession.getId());
    }
}
