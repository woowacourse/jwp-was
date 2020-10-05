package jwp.was.webapplicationserver.controller;

import static com.google.common.net.HttpHeaders.LOCATION;
import static jwp.was.util.Constants.HEADERS_EMPTY;
import static jwp.was.util.Constants.HTTP_VERSION;
import static jwp.was.util.Constants.PARAMETERS_FOR_CREATE_USER;
import static jwp.was.util.Constants.URL_PATH_API_CREATE_USER;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static jwp.was.webserver.FileNameExtension.API;
import static jwp.was.webserver.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

import jwp.was.webapplicationserver.configure.ConfigureMaker;
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

    @DisplayName("User 생성, 200 반환")
    @Test
    void createUser_Return200() {
        HttpRequest httpRequest = makeHttpRequest(PARAMETERS_FOR_CREATE_USER);

        HttpResponse httpResponse = userController.createUser(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(httpResponse.getBody()).isEmpty();
        assertThat(httpResponse.getHttpVersion()).isEqualTo(HTTP_VERSION.getHttpVersion());
        assertThat(httpResponse.getHeaders().get(LOCATION))
            .isEqualTo(URL_PATH_INDEX_HTML.getUrlPath());
    }

    private HttpRequest makeHttpRequest(Parameters parameters) {
        return new HttpRequest(POST, URL_PATH_API_CREATE_USER,
            parameters, HTTP_VERSION, HEADERS_EMPTY, API);
    }
}
