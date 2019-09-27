package servlet.response;

import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServletResponseTest {

    @Test
    @DisplayName("200 OK HttpServletResponse")
    public void HttpResponse200() {
        final String path = "/user/list.html";
        HttpServletResponse httpServletResponse = new HttpServletResponse();
        httpServletResponse.addHeader("Content-Type", "text/html");
        httpServletResponse.addCookie("logined", "true");
        httpServletResponse.addCookie("Path", "/");
        httpServletResponse.forward(path);

        assertThat(httpServletResponse.getCookie()).isEqualTo("logined=true; Path=/");
        assertThat(httpServletResponse.getResourcePath()).isEqualTo("/user/list.html");
    }


    @Test
    @DisplayName("302 FOUND HttpServletResponse")
    public void HttpResponse302() {
        final String path = "/index.html";
        HttpServletResponse httpServletResponse = new HttpServletResponse();
        httpServletResponse.addHeader("Content-Type", "text/html");
        httpServletResponse.addCookie("logined", "true");
        httpServletResponse.addCookie("Path", "/");
        httpServletResponse.sendRedirect(path);

        assertThat(httpServletResponse.getCookie()).isEqualTo("logined=true; Path=/");
    }
}