package jwp.was.webapplicationserver.configure.security;

import static com.google.common.net.HttpHeaders.COOKIE;
import static com.google.common.net.HttpHeaders.LOCATION;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jwp.was.webapplicationserver.configure.controller.info.HttpInfo;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

public class WithLoginConfigure {

    public static final String SET_COOKIE_SESSION_ID_KEY = "sessionId=";
    public static final String ATTRIBUTE_KEY_USER = "USER";
    private static final WithLoginConfigure INSTANCE = new WithLoginConfigure();
    private static final String NEED_LOGIN_MESSAGE = "로그인이 필요합니다.";
    private static final String LOGIN_PAGE = "/user/login.html";

    private final Set<HttpInfo> withLogin;

    public WithLoginConfigure() {
        Set<HttpInfo> withLogin = new HashSet<>();
        withLogin.add(HttpInfo.of(HttpMethod.GET, "/user/list"));
        this.withLogin = Collections.unmodifiableSet(withLogin);
    }

    public static WithLoginConfigure getInstance() {
        return INSTANCE;
    }

    public boolean verifyLogin(HttpRequest httpRequest) {
        HttpInfo requestHttpInfo
            = HttpInfo.of(httpRequest.getHttpMethod(), httpRequest.getUrlPath());

        if (withLogin.contains(requestHttpInfo)) {
            Cookie cookie = new Cookie(httpRequest.getHeader(COOKIE));
            return cookie.verifySessionId();
        }
        return true;
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
