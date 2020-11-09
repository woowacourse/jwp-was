package jwp.was.webapplicationserver.configure.security;

import static com.google.common.net.HttpHeaders.LOCATION;
import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static jwp.was.webserver.HttpStatusCode.FOUND;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jwp.was.webapplicationserver.configure.annotation.UserDetails;
import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import jwp.was.webapplicationserver.controller.dto.LoginRequest;
import jwp.was.webapplicationserver.controller.dto.LoginRequestAssembler;
import jwp.was.webapplicationserver.service.UserService;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

public class LoginFilter {

    static final String SET_COOKIE_SESSION_ID = "sessionId";
    private static final String SET_COOKIE_SESSION_ID_KEY = SET_COOKIE_SESSION_ID + "=";
    private static final String INDEX_HTML = "/index.html";
    private static final String EMPTY_BODY = "";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    private static final String SET_COOKIE_ALL_PATH = "; Path=/";
    private static final String USER_LOGIN_URL_PATH = "/user/login";

    private static final LoginFilter INSTANCE = new LoginFilter();

    private UserService userService;

    private LoginFilter() {
        Set<Object> configures = ConfigureMaker.getInstance()
            .getConfiguresWithAnnotation(UserDetails.class);
        for (Object configure : configures) {
            userService = (UserService) configure;
        }
    }

    public static LoginFilter getInstance() {
        return INSTANCE;
    }

    public HttpResponse login(HttpRequest httpRequest) {
        LoginRequest loginRequest = LoginRequestAssembler.assemble(httpRequest.getParameters());
        Map<String, String> headers = new HashMap<>();
        if (userService.login(loginRequest)) {
            headers.put(SET_COOKIE, makeLoginSuccessCookie(loginRequest));
            headers.put(LOCATION, INDEX_HTML);
            return HttpResponse.of(httpRequest.getHttpVersion(), FOUND, headers, EMPTY_BODY);
        }
//        headers.put(SET_COOKIE, SET_COOKIE_SESSION_ID_KEY);
        headers.put(LOCATION, LOGIN_FAILED_HTML);
        return HttpResponse.of(httpRequest.getHttpVersion(), FOUND, headers, EMPTY_BODY);
    }

    private String makeLoginSuccessCookie(LoginRequest loginRequest) {
        String sessionId = userService.createSessionById(loginRequest.getUserId());
        return SET_COOKIE_SESSION_ID_KEY + sessionId + SET_COOKIE_ALL_PATH;
    }

    public boolean isLoginRequest(HttpRequest httpRequest) {
        return HttpMethod.POST.isSame(httpRequest.getHttpMethod())
            && USER_LOGIN_URL_PATH.equals(httpRequest.getUrlPath());
    }
}
