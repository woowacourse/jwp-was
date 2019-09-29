package webserver.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.resolver.FileResolver;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class FileServletTest extends AbstractServletTest {
    FileServlet fileServlet;
    @BeforeEach
    void setup() throws IOException {
        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        resolver = new FileResolver();
        fileServlet = new FileServlet(resolver);
    }

    @DisplayName("정적 html파일 가져오기")
    @Test
    void run_httpFileRequest_ok() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getCommonGetRequest("/index.html");
        View view = resolver.createView("/index.html");
        assertThat(fileServlet.run(httpRequest, httpResponse)).isEqualTo(new ModelAndView(view));
    }


    @DisplayName("정적 css파일 가져오기")
    @Test
    void run_() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getCommonGetRequest("/css.css");
        View view = resolver.createView("/css.css");
        assertThat(fileServlet.run(httpRequest, httpResponse)).isEqualTo(new ModelAndView(view));
    }

}