package http.controller;

import http.model.request.HttpMethod;
import http.model.request.ServletRequest;
import http.model.response.ServletResponse;

import java.io.ByteArrayOutputStream;

public abstract class BaseControllerTest {
    protected ServletRequest.Builder getDefaultRequest(HttpMethod method, String uri) {
        return ServletRequest.builder()
                .requestLine(method, uri, "HTTP/1.1");
    }

    protected ServletResponse getDefaultResponse() {
        return new ServletResponse(new ByteArrayOutputStream());
    }

    protected boolean isLogin(ServletResponse servletResponse) {
        return servletResponse.getHeader("Set-Cookie").equals("logined=true; Path=/");
    }
}
