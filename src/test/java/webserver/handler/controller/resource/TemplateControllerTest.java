package webserver.handler.controller.resource;

import org.junit.jupiter.api.Test;
import webserver.handler.controller.Controller;
import webserver.http.HttpStatus;
import webserver.http.MediaType;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestHeaderParser;
import webserver.http.response.HttpResponse;
import webserver.view.TemplateResourceResolver;
import webserver.view.ViewLocation;
import webserver.view.ViewResolver;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateControllerTest {
    @Test
    void html_파일_요청() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/java/data/html_request.txt");
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        HttpRequest httpRequest = RequestHeaderParser.parseRequest(inputStream);
        HttpResponse httpResponse = HttpResponse.of();
        ViewResolver viewResolver = new TemplateResourceResolver();

        Controller controller = new TemplateController(viewResolver);
        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getPath()).isEqualTo(ViewLocation.TEMPLATE.getLocation() + "/user/form.html");
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatus.OK.getValue());
        assertThat(httpResponse.getMediaType()).isEqualTo(MediaType.HTML.getContentType());
    }
}