package webserver.handler.controller.resource;

import org.junit.jupiter.api.Test;
import webserver.handler.controller.Controller;
import webserver.http.HttpStatus;
import webserver.http.MediaType;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestHeaderParser;
import webserver.http.response.HttpResponse;
import webserver.view.StaticResourceResolver;
import webserver.view.ViewLocation;
import webserver.view.ViewResolver;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceControllerTest {
    @Test
    void 정적_자원_요청() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/java/data/css_request.txt");
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        HttpRequest httpRequest = RequestHeaderParser.parseRequest(inputStream);
        HttpResponse httpResponse = HttpResponse.of();
        ViewResolver viewResolver = new StaticResourceResolver();

        Controller controller = new ResourceController(viewResolver);
        controller.service(httpRequest, httpResponse);

        assertThat(httpResponse.getPath()).isEqualTo(ViewLocation.STATIC.getLocation() + "/css/styles.css");
        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatus.OK.getValue());
        assertThat(httpResponse.getMediaType()).isEqualTo(MediaType.CSS.getContentType());
    }
}