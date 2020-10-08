package jwp.was.webapplicationserver.controller;

import static com.google.common.net.HttpHeaders.LOCATION;
import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static jwp.was.webapplicationserver.configure.security.LoginConfigure.SET_COOKIE_SESSION_ID_KEY;
import static jwp.was.webserver.HttpStatusCode.FOUND;

import java.util.HashMap;
import java.util.Map;
import jwp.was.webapplicationserver.configure.annotation.Autowired;
import jwp.was.webapplicationserver.configure.annotation.RequestMapping;
import jwp.was.webapplicationserver.configure.annotation.RestController;
import jwp.was.webapplicationserver.controller.dto.LoginRequest;
import jwp.was.webapplicationserver.controller.dto.LoginRequestAssembler;
import jwp.was.webapplicationserver.controller.dto.UserRequest;
import jwp.was.webapplicationserver.controller.dto.UserRequestAssembler;
import jwp.was.webapplicationserver.service.UserService;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

@RestController
public class UserController {

    private static final String INDEX_HTML = "/index.html";
    private static final String EMPTY_BODY = "";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    private static final String SET_COOKIE_ALL_PATH = "; Path=/";

    @Autowired
    private UserService userService;

    @RequestMapping(method = HttpMethod.POST, urlPath = "/user/create")
    public HttpResponse createUser(HttpRequest httpRequest) {
        UserRequest userRequest = UserRequestAssembler.assemble(httpRequest.getParameters());
        userService.createUser(userRequest);
        Map<String, String> headers = new HashMap<>();
        headers.put(LOCATION, INDEX_HTML);
        return HttpResponse.of(httpRequest.getHttpVersion(), FOUND, headers, EMPTY_BODY);
    }

    @RequestMapping(method = HttpMethod.POST, urlPath = "/user/login")
    public HttpResponse login(HttpRequest httpRequest) {
        LoginRequest loginRequest = LoginRequestAssembler.assemble(httpRequest.getParameters());
        Map<String, String> headers = new HashMap<>();
        if (userService.login(loginRequest)) {
            headers.put(SET_COOKIE, makeLoginSuccessCookie(loginRequest));
            headers.put(LOCATION, INDEX_HTML);
            return HttpResponse.of(httpRequest.getHttpVersion(), FOUND, headers, EMPTY_BODY);
        }
        headers.put(SET_COOKIE, SET_COOKIE_SESSION_ID_KEY);
        headers.put(LOCATION, LOGIN_FAILED_HTML);
        return HttpResponse.of(httpRequest.getHttpVersion(), FOUND, headers, EMPTY_BODY);
    }

    private String makeLoginSuccessCookie(LoginRequest loginRequest) {
        String sessionId = userService.createSessionById(loginRequest.getUserId());
        return SET_COOKIE_SESSION_ID_KEY + sessionId + SET_COOKIE_ALL_PATH;
    }
}
