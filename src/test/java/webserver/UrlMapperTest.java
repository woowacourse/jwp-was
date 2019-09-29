package webserver;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UrlMapperTest extends WebTestForm {

    @Test
    void 컨트롤러_가지_않는_요청_테스트() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/css/bootstrap.css");
        UrlMapper urlMapper = new UrlMapper();
        View view = urlMapper.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/css/bootstrap.css");
    }
}