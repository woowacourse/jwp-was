package jwp.was.webapplicationserver.controller;

import static com.google.common.net.HttpHeaders.LOCATION;
import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static jwp.was.util.Constants.HEADERS_EMPTY;
import static jwp.was.util.Constants.HTTP_VERSION;
import static jwp.was.util.Constants.PARAMETERS_FOR_CREATE_USER;
import static jwp.was.util.Constants.PARAMETERS_FOR_LOGIN;
import static jwp.was.util.Constants.URL_PATH_API_CREATE_USER;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static jwp.was.webserver.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import jwp.was.webapplicationserver.db.DataBaseTest;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;
import jwp.was.webserver.dto.Parameters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    private ConfigureMaker configureMaker = ConfigureMaker.getInstance();
    private UserController userController = configureMaker.getConfigure(UserController.class);

    @AfterEach
    void tearDown() {
        DataBaseTest.clear();
    }

    @DisplayName("User 생성, 성공 - Location + Found 반환")
    @Test
    void createUser_Success_ReturnFoundWithLocation() {
        HttpRequest createUserRequest = makeHttpRequest(PARAMETERS_FOR_CREATE_USER);

        HttpResponse createUserResponse = userController.createUser(createUserRequest);

        assertThat(createUserResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(createUserResponse.getBody()).isEmpty();
        assertThat(createUserResponse.getHttpVersion()).isEqualTo(HTTP_VERSION.getHttpVersion());
        assertThat(createUserResponse.getHeaders().get(LOCATION))
            .isEqualTo(URL_PATH_INDEX_HTML.getUrlPath());
    }

    @DisplayName("Login 생성, 성공 - Location + Found 반환")
    @Test
    void login_Success_ReturnFoundWithLocation() {
        HttpRequest createUserRequest = makeHttpRequest(PARAMETERS_FOR_CREATE_USER);
        userController.createUser(createUserRequest);

        HttpRequest loginRequest = makeHttpRequest(PARAMETERS_FOR_LOGIN);
        HttpResponse loginResponse = userController.login(loginRequest);

        assertThat(loginResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(loginResponse.getBody()).isEmpty();
        assertThat(loginResponse.getHttpVersion()).isEqualTo(HTTP_VERSION.getHttpVersion());
        assertThat(loginResponse.getHeaders().get(SET_COOKIE)).isEqualTo("logined=true; Path=/");
        assertThat(loginResponse.getHeaders().get(LOCATION))
            .isEqualTo(URL_PATH_INDEX_HTML.getUrlPath());
    }

    @DisplayName("Login 생성, 실패 - Location + Found 반환")
    @Test
    void login_Failed_ReturnFoundWithLocation() {
        HttpRequest loginRequest = makeHttpRequest(PARAMETERS_FOR_LOGIN);
        HttpResponse loginResponse = userController.login(loginRequest);

        assertThat(loginResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(loginResponse.getBody()).isEmpty();
        assertThat(loginResponse.getHttpVersion()).isEqualTo(HTTP_VERSION.getHttpVersion());
        assertThat(loginResponse.getHeaders().get(SET_COOKIE)).isEqualTo("logined=false");
        assertThat(loginResponse.getHeaders().get(LOCATION)).isEqualTo("/user/login_failed.html");
    }

    private HttpRequest makeHttpRequest(Parameters parameters) {
        return new HttpRequest(POST, URL_PATH_API_CREATE_USER, parameters, HTTP_VERSION,
            HEADERS_EMPTY);
    }
}
