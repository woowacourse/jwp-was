package jwp.was.webapplicationserver.configure.security;

import static com.google.common.net.HttpHeaders.LOCATION;
import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static jwp.was.util.Constants.HEADERS_EMPTY;
import static jwp.was.util.Constants.HTTP_VERSION;
import static jwp.was.util.Constants.PARAMETERS_FOR_CREATE_USER;
import static jwp.was.util.Constants.PARAMETERS_FOR_LOGIN;
import static jwp.was.util.Constants.SET_COOKIE_SESSION_ID_KEY;
import static jwp.was.util.Constants.URL_PATH_API_CREATE_USER;
import static jwp.was.util.Constants.URL_PATH_INDEX_HTML;
import static jwp.was.util.Constants.URL_PATH_LOGIN;
import static jwp.was.util.Constants.WRONG_PARAMETERS_FOR_LOGIN;
import static org.assertj.core.api.Assertions.assertThat;
import static util.HttpMethod.GET;
import static util.HttpMethod.POST;

import dto.HttpRequest;
import dto.HttpResponse;
import dto.Parameters;
import java.util.HashMap;
import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import jwp.was.webapplicationserver.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpStatusCode;

class LoginFilterTest {

    private static final String SET_COOKIE_ALL_PATH = "; Path=/";

    private final ConfigureMaker configureMaker = ConfigureMaker.getInstance();
    private final UserController userController = configureMaker.getConfigure(UserController.class);
    private final LoginFilter LOGIN_FILTER = LoginFilter.getInstance();

    @DisplayName("LoginRequest인지 확인 - True, Method Path 모두 매칭")
    @Test
    void isLoginRequest_MatchedMethodAndPath_ReturnTrue() {
        HttpRequest httpRequest = new HttpRequest(POST, URL_PATH_LOGIN,
            new Parameters(new HashMap<>()), HTTP_VERSION, HEADERS_EMPTY);

        assertThat(LOGIN_FILTER.isLoginRequest(httpRequest)).isTrue();
    }

    @DisplayName("LoginRequest인지 확인 - False, Method 매칭 안 됨")
    @Test
    void isLoginRequest_NotMatchedMethod_ReturnFalse() {
        HttpRequest httpRequest = new HttpRequest(GET, URL_PATH_LOGIN,
            new Parameters(new HashMap<>()), HTTP_VERSION, HEADERS_EMPTY);

        assertThat(LOGIN_FILTER.isLoginRequest(httpRequest)).isFalse();
    }

    @DisplayName("LoginRequest인지 확인 - False, Path 매칭 안 됨")
    @Test
    void isLoginRequest_NotMatchedPath_ReturnFalse() {
        HttpRequest httpRequest = new HttpRequest(POST, URL_PATH_API_CREATE_USER,
            new Parameters(new HashMap<>()), HTTP_VERSION, HEADERS_EMPTY);

        assertThat(LOGIN_FILTER.isLoginRequest(httpRequest)).isFalse();
    }

    @DisplayName("LoginRequest인지 확인 - False, Method Path 모두 매칭 안 됨")
    @Test
    void isLoginRequest_NotMatchedMethodAndPath_ReturnFalse() {
        HttpRequest httpRequest = new HttpRequest(GET, URL_PATH_API_CREATE_USER,
            new Parameters(new HashMap<>()), HTTP_VERSION, HEADERS_EMPTY);

        assertThat(LOGIN_FILTER.isLoginRequest(httpRequest)).isFalse();
    }

    @DisplayName("Login 생성, 성공 - Location + Found 반환")
    @Test
    void login_Success_ReturnFoundWithLocation() {
        HttpRequest createUserRequest = makeHttpRequest(PARAMETERS_FOR_CREATE_USER);
        userController.createUser(createUserRequest);

        HttpRequest loginRequest = makeHttpRequest(PARAMETERS_FOR_LOGIN);
        HttpResponse loginResponse = LOGIN_FILTER.login(loginRequest);

        assertThat(loginResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(loginResponse.getBody()).isEmpty();
        assertThat(loginResponse.getHttpVersion()).isEqualTo(HTTP_VERSION.getHttpVersion());
        assertThat(loginResponse.getHeaders().get(SET_COOKIE)).contains(SET_COOKIE_SESSION_ID_KEY);
        assertThat(loginResponse.getHeaders().get(SET_COOKIE)).contains(SET_COOKIE_ALL_PATH);
        assertThat(loginResponse.getHeaders().get(LOCATION))
            .isEqualTo(URL_PATH_INDEX_HTML.getUrlPath());
    }

    @DisplayName("Login 생성, 실패 - Location + Found 반환")
    @Test
    void login_Failed_ReturnFoundWithLocation() {
        HttpRequest loginRequest = makeHttpRequest(WRONG_PARAMETERS_FOR_LOGIN);
        HttpResponse loginResponse = LOGIN_FILTER.login(loginRequest);

        assertThat(loginResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(loginResponse.getBody()).isEmpty();
        assertThat(loginResponse.getHttpVersion()).isEqualTo(HTTP_VERSION.getHttpVersion());
        assertThat(loginResponse.getHeaders().get(SET_COOKIE)).isNull();
        assertThat(loginResponse.getHeaders().get(LOCATION)).isEqualTo("/user/login_failed.html");
    }

    private HttpRequest makeHttpRequest(Parameters parameters) {
        return new HttpRequest(POST, URL_PATH_API_CREATE_USER, parameters, HTTP_VERSION,
            HEADERS_EMPTY);
    }
}
