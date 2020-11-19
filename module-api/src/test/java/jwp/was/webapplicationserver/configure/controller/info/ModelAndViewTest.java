package jwp.was.webapplicationserver.configure.controller.info;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import jwp.was.dto.HttpRequest;
import jwp.was.dto.HttpResponse;
import jwp.was.dto.HttpVersion;
import jwp.was.dto.UrlPath;
import jwp.was.util.HttpMethod;
import jwp.was.util.HttpStatusCode;
import jwp.was.webapplicationserver.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModelAndViewTest {

    @DisplayName("ModelAndView 객체 HttpResponse로 변환 - OK 반환, 파일 존재")
    @Test
    void toHttpResponse_ExistsFile_ReturnOk() {
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, UrlPath.from("/user/list"),
            null, HttpVersion.of("HTTP/1.1"), null);

        User user1 = new User("1", "1pass", "1name", "1email");
        User user2 = new User("2", "2pass", "2name", "2email");
        Object model = Arrays.asList(user1, user2);
        String view = "/user/list";
        ModelAndView modelAndView = new ModelAndView(model, view);

        HttpResponse httpResponse = modelAndView.toHttpResponse(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
        assertThat(httpResponse.getBody()).contains(user1.getName().getBytes());
        assertThat(httpResponse.getBody()).contains(user2.getEmail().getBytes());
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo("text/html;charset=utf-8");
    }

    @DisplayName("ModelAndView 객체 HttpResponse로 변환 - BadRequest 반환, 파일 미존재")
    @Test
    void toHttpResponse_NotExistsFile_ReturnNotFound() {
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, UrlPath.from("/user/list"),
            null, HttpVersion.of("HTTP/1.1"), null);

        User user1 = new User("1", "1pass", "1name", "1email");
        User user2 = new User("2", "2pass", "2name", "2email");
        Object model = Arrays.asList(user1, user2);
        String view = "/user/wrongList";
        ModelAndView modelAndView = new ModelAndView(model, view);

        HttpResponse httpResponse = modelAndView.toHttpResponse(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.NOT_FOUND);
        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
        assertThat(httpResponse.getBody()).doesNotContain(user1.getName().getBytes());
        assertThat(httpResponse.getBody()).doesNotContain(user2.getEmail().getBytes());
        assertThat(httpResponse.getHeaders().get(CONTENT_TYPE))
            .isEqualTo("text/plain;charset=utf-8");
    }
}
