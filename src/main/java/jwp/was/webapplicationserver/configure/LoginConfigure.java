package jwp.was.webapplicationserver.configure;

import static com.google.common.net.HttpHeaders.COOKIE;
import static com.google.common.net.HttpHeaders.LOCATION;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import jwp.was.webapplicationserver.configure.controller.info.HttpInfo;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

public class LoginConfigure {

    private static final LoginConfigure INSTANCE = new LoginConfigure();
    private static final String NEED_LOGIN_MESSAGE = "로그인이 필요합니다.";
    private static final String LOGIN_PAGE = "/login.html";
    private static final String LOGIN_TRUE = "logined=true";

    private final Set<HttpInfo> withLogin;

    public LoginConfigure() {
        Set<HttpInfo> withLogin = new HashSet<>();
        withLogin.add(HttpInfo.of(HttpMethod.GET, "/user/list"));
        this.withLogin = Collections.unmodifiableSet(withLogin);
    }

    public static LoginConfigure getInstance() {
        return INSTANCE;
    }

    public boolean verifyLogin(HttpRequest httpRequest) {
        HttpInfo requestHttpInfo
            = HttpInfo.of(httpRequest.getHttpMethod(), httpRequest.getUrlPath());

        if (withLogin.contains(requestHttpInfo)) {
            String cookie = httpRequest.getHeader(COOKIE);
            return verifyCookie(cookie);
        }
        return true;
    }

    private boolean verifyCookie(String cookie) {
        if (Objects.isNull(cookie)) {
            return false;
        }
        return cookie.contains(LOGIN_TRUE);
    }

    public HttpResponse getRedirectLoginPage(HttpRequest httpRequest) {
        Map<String, String> headers = new HashMap<>();
        headers.put(LOCATION, LOGIN_PAGE);
        return HttpResponse.of(httpRequest.getHttpVersion(), HttpStatusCode.FOUND, headers,
            NEED_LOGIN_MESSAGE);
    }

    public Set<HttpInfo> getWithLoginInfo() {
        return withLogin;
    }
}
