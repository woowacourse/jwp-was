package controller;

import static com.google.common.net.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_API_CREATE_USER;
import static util.Constants.URL_INDEX_HTML;
import static util.Constants.USER_EMAIL;
import static util.Constants.USER_ID;
import static util.Constants.USER_NAME;
import static util.Constants.USER_PASSWORD;

import db.DataBase;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.FileNameExtension;
import webserver.HttpMethod;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

class UserControllerTest {

    private ControllerAdvice controllerAdvice = new ControllerAdvice();
    private DataBase dataBase = new DataBase();
    private UserService userService = new UserService(dataBase);
    private UserController userController = new UserController(controllerAdvice, userService);

    @AfterEach
    void tearDown() {
        dataBase.clear();
    }

    @DisplayName("User 생성, 200 반환")
    @Test
    void createUser_Return200() {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put(USER_ID, USER_ID);
            put(USER_EMAIL, USER_EMAIL);
            put(USER_PASSWORD, USER_PASSWORD);
            put(USER_NAME, USER_NAME);
        }};
        HttpRequest httpRequest = new HttpRequest(HttpMethod.POST.name(), URL_API_CREATE_USER,
            parameter, PROTOCOL, new HashMap<>(), FileNameExtension.API);

        HttpResponse httpResponse = userController.createUser(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(httpResponse.getBody()).isEmpty();
        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHeaders().get(LOCATION)).isEqualTo(URL_INDEX_HTML);
    }

    @DisplayName("User 생성, 404 반환 - 파라미터 일부 누락")
    @Test
    void createUser_NotExistsSomeParameters_Return404() {
        Map<String, String> parameter = new HashMap<String, String>() {{
            put(USER_ID, USER_ID);
            put(USER_EMAIL, USER_EMAIL);
            put(USER_PASSWORD, USER_PASSWORD);
        }};
        HttpRequest httpRequest = new HttpRequest(HttpMethod.POST.name(), URL_API_CREATE_USER,
            parameter, PROTOCOL, new HashMap<>(), FileNameExtension.API);

        HttpResponse httpResponse = userController.createUser(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.BAD_REQUEST);
        byte[] expectedBody = HttpStatusCode.BAD_REQUEST.getMessage()
            .getBytes(StandardCharsets.UTF_8);
        assertThat(httpResponse.getBody()).contains(expectedBody);
        assertThat(httpResponse.getProtocol()).isEqualTo(PROTOCOL);
        assertThat(httpResponse.getHeaders().get(LOCATION)).isNull();
    }
}