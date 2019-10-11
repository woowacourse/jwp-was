package webserver.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.*;
import webserver.servlet.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MappingHandlerTest {
    HttpRequest getRequest(String uri) {
        return new HttpRequest(new RequestLine("GET", uri, "HTTP/1.1")
                , new RequestHeader(new HashMap<>()),
                new RequestBody(null),
                new Cookie(null));
    }

    HttpRequest getRequestWithAccept(String uri) {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Accept","text/html");
        return new HttpRequest(new RequestLine("GET", uri, "HTTP/1.1")
                , new RequestHeader(requestHeader),
                new RequestBody(null),
                new Cookie(null));
    }

    @DisplayName("리퀘스트 서블릿 매핑")
    @Test
    void getServlets_request_true() {
        HttpServlet homeServlet = MappingHandler.getServlets(getRequest("/"));
        HttpServlet userCreateServlet = MappingHandler.getServlets(getRequest("/user/create"));
        HttpServlet userListServlet = MappingHandler.getServlets(getRequest("/user/list"));
        HttpServlet userLoginServlet = MappingHandler.getServlets(getRequest("/user/login"));
        assertThat(homeServlet).isInstanceOf(HomeServlet.class);
        assertThat(userCreateServlet).isInstanceOf(UserCreateServlet.class);
        assertThat(userListServlet).isInstanceOf(UserListServlet.class);
        assertThat(userLoginServlet).isInstanceOf(UserLoginServlet.class);
    }

    @DisplayName("정적 파일 서블릿 매핑")
    @Test
    void getServlets_staticFile_true() {
        HttpServlet fileServlet = MappingHandler.getServlets(getRequestWithAccept("/abc.html"));
        assertThat(fileServlet).isInstanceOf(FileServlet.class);
    }
}