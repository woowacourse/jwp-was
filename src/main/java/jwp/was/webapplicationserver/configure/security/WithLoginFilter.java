package jwp.was.webapplicationserver.configure.security;

import static com.google.common.net.HttpHeaders.COOKIE;
import static com.google.common.net.HttpHeaders.LOCATION;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jwp.was.webapplicationserver.configure.controller.info.HttpInfo;
import jwp.was.webapplicationserver.configure.session.HttpSessions;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.HttpStatusCode;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

public class WithLoginFilter {

    public static final String ATTRIBUTE_KEY_USER = "USER";
    private static final String SET_COOKIE_SESSION_ID = "sessionId";
    public static final String SET_COOKIE_SESSION_ID_KEY = SET_COOKIE_SESSION_ID + "=";
    private static final WithLoginFilter INSTANCE = new WithLoginFilter();
    private static final String NEED_LOGIN_MESSAGE = "로그인이 필요합니다.";
    private static final String LOGIN_PAGE = "/user/login.html";
    private static final HttpSessions HTTP_SESSIONS = HttpSessions.getInstance();

    private final Set<HttpInfo> withLogin;

    public WithLoginFilter() {
        Set<HttpInfo> withLogin = new HashSet<>();
        withLogin.add(HttpInfo.of(HttpMethod.GET, "/user/list"));
        this.withLogin = Collections.unmodifiableSet(withLogin);
    }

    public static WithLoginFilter getInstance() {
        return INSTANCE;
    }

    public boolean verifyLogin(HttpRequest httpRequest) {
        HttpInfo requestHttpInfo
            = HttpInfo.of(httpRequest.getHttpMethod(), httpRequest.getUrlPath());

        if (withLogin.contains(requestHttpInfo)) {
            Cookies cookies = Cookies.from(httpRequest.getHeader(COOKIE));
            String sessionId = cookies.get(SET_COOKIE_SESSION_ID);
            return HTTP_SESSIONS.existsUser(sessionId);
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
