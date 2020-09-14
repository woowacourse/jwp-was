package controller;

import static com.google.common.net.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.HEADERS_EMPTY;
import static util.Constants.PARAMETERS_FOR_CREATE_USER;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_PATH_API_CREATE_USER;
import static util.Constants.URL_PATH_INDEX_HTML;
import static util.Constants.USER_EMAIL;
import static util.Constants.USER_ID;
import static util.Constants.USER_PASSWORD;
import static webserver.FileNameExtension.API;
import static webserver.HttpMethod.POST;

import db.DataBase;
import db.DataBaseTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;
import webserver.dto.Parameters;

class UserControllerTest {

    private ControllerAdvice controllerAdvice = new ControllerAdvice();
    private DataBase dataBase = new DataBase();
    private UserService userService = new UserService(dataBase);
    private UserController userController = new UserController(controllerAdvice, userService);

    @AfterEach
    void tearDown() {
        DataBaseTest.clear();
    }

    @DisplayName("User 생성, 200 반환")
    @Test
    void createUser_Return200() {
        HttpRequest httpRequest = makeHttpRequest(PARAMETERS_FOR_CREATE_USER);

        HttpResponse httpResponse = userController.createUser(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(httpResponse.getBody()).isEmpty();
        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL.getProtocol());
        assertThat(httpResponse.getHeaders().get(LOCATION))
            .isEqualTo(URL_PATH_INDEX_HTML.getUrlPath());
    }

    @DisplayName("User 생성, 404 반환 - 파라미터 일부 누락")
    @Test
    void createUser_NotExistsSomeParameters_Return404() {
        Parameters wrongParameters = Parameters.fromEncodedParameter(
            USER_ID + "=" + USER_ID
                + "&" + USER_EMAIL + "=" + USER_EMAIL
                + "&" + USER_PASSWORD + "=" + USER_PASSWORD
        );
        HttpRequest httpRequest = makeHttpRequest(wrongParameters);

        HttpResponse httpResponse = userController.createUser(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        byte[] expectedBody = HttpStatusCode.BAD_REQUEST.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL.getProtocol());
        assertThat(httpResponse.getHeaders().get(LOCATION)).isNull();
    }

    private HttpRequest makeHttpRequest(Parameters parameters) {
        return new HttpRequest(POST, URL_PATH_API_CREATE_USER,
            parameters, PROTOCOL, HEADERS_EMPTY, API);
    }
}