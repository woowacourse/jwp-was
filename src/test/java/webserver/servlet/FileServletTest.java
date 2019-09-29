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

import static org.assertj.core.api.Assertions.assertThat;

class FileServletTest extends AbstractServletTest {
    HttpResponse httpResponse;
    FileServlet fileServlet;
    FileResolver fileResolver;

    @BeforeEach
    void setup() throws IOException {

        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        fileResolver = new FileResolver();
        fileServlet = new FileServlet(new FileResolver());
    }

    @DisplayName("정적 html파일 가져오기")
    @Test
    void run_httpFileRequest_ok() throws IOException {
        HttpRequest httpRequest = getFileRequest("/index.html");
        View view = fileResolver.createView("/index.html");
        assertThat(fileServlet.run(httpRequest, httpResponse)).isEqualTo(new ModelAndView(view));
    }


    @DisplayName("정적 css파일 가져오기")
    @Test
    void run_() throws IOException {
        HttpRequest httpRequest = getFileRequest("/css.css");
        View view = fileResolver.createView("/css.css");
        assertThat(fileServlet.run(httpRequest, httpResponse)).isEqualTo(new ModelAndView(view));
    }

}