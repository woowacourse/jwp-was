package jwp.was.webapplicationserver.configure.security;

import static com.google.common.net.HttpHeaders.COOKIE;
import static com.google.common.net.HttpHeaders.LOCATION;
import static jwp.was.webapplicationserver.configure.security.LoginFilter.SET_COOKIE_SESSION_ID;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import jwp.was.dto.HttpRequest;
import jwp.was.dto.HttpResponse;
import jwp.was.util.HttpMethod;
import jwp.was.util.HttpStatusCode;
import jwp.was.webapplicationserver.configure.controller.info.HttpInfo;
import jwp.was.webapplicationserver.configure.session.HttpSessions;

public class WithLoginFilter {

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

    public void validateLogin(HttpRequest httpRequest) {
        HttpInfo requestHttpInfo
            = HttpInfo.of(httpRequest.getHttpMethod(), httpRequest.getUrlPath());

        if (!withLogin.contains(requestHttpInfo)) {
            return;
        }

        Cookies cookies = Cookies.from(httpRequest.getHeader(COOKIE));
        String sessionId = cookies.get(SET_COOKIE_SESSION_ID);
        if (Objects.isNull(sessionId) || sessionId.isEmpty()) {
            throw new NeedLoginException("Session ID 값이 없습니다.");
        }

        if (HTTP_SESSIONS.existsUser(sessionId)) {
            return;
        }

        throw new NeedLoginException("Session ID가 유효하지 않습니다 : " + sessionId);
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
