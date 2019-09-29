package webserver.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.resolver.HtmlViewResolver;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class HomeServletTest extends AbstractServletTest {
    @BeforeEach
    void setup() {
        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        resolver = new HtmlViewResolver();
        httpServlet = new HomeServlet(resolver);
    }

    @DisplayName("index로 get요청")
    @Test
    void doGet() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getCommonGetRequest("/");
        View view = resolver.createView("/index");
        assertThat(httpServlet.doGet(httpRequest, httpResponse).getView()).isEqualTo(new ModelAndView(view).getView());
    }
}