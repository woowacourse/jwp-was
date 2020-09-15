package controller;

import static com.google.common.net.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.HEADERS_EMPTY;
import static util.Constants.PARAMETERS_FOR_CREATE_USER;
import static util.Constants.PROTOCOL;
import static util.Constants.URL_PATH_API_CREATE_USER;
import static util.Constants.URL_PATH_INDEX_HTML;
import static webserver.FileNameExtension.API;
import static webserver.HttpMethod.POST;

import db.DataBase;
import db.DataBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;
import webserver.dto.Parameters;

class UserControllerTest {

    private DataBase dataBase = new DataBase();
    private UserService userService = new UserService(dataBase);
    private UserController userController = new UserController(userService);

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

    private HttpRequest makeHttpRequest(Parameters parameters) {
        return new HttpRequest(POST, URL_PATH_API_CREATE_USER,
            parameters, PROTOCOL, HEADERS_EMPTY, API);
    }
}