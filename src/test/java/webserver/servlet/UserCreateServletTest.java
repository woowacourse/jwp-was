package webserver.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.resolver.HtmlViewResolver;

import java.io.DataOutputStream;

class UserCreateServletTest extends AbstractServletTest {
    @BeforeEach
    void setup() {
        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        resolver = new HtmlViewResolver();
        httpServlet = new HomeServlet(resolver);
    }

    @DisplayName("유저 저장")
    void doPost_userInformation_true() {

    }
}