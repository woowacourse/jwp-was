package jwp.was.webapplicationserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import jwp.was.dto.HttpRequest;
import jwp.was.dto.HttpResponse;
import jwp.was.dto.HttpVersion;
import jwp.was.dto.UrlPath;
import jwp.was.util.HttpMethod;
import jwp.was.util.HttpStatusCode;
import jwp.was.webapplicationserver.configure.controller.info.ModelAndView;
import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserPageControllerTest {

    private ConfigureMaker configureMaker = ConfigureMaker.getInstance();
    private UserPageController userPageController
        = configureMaker.getConfigure(UserPageController.class);

    @DisplayName("전체 유저 페이지 조회 - 성공, Ok 반환")
    @Test
    void getUserListPage_Success_ReturnOk() {
        HttpRequest httpRequest = new HttpRequest(HttpMethod.GET, UrlPath.from("/user/list"),
            null, HttpVersion.of("HTTP/1.1"), null);

        ModelAndView modelAndView = userPageController.getUserListPage(httpRequest);
        HttpResponse httpResponse = modelAndView.toHttpResponse(httpRequest);

        assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK);
        assertThat(httpResponse.getHttpVersion()).isEqualTo(httpRequest.getHttpVersion());
        assertThat(httpResponse.getBody().length).isNotZero();
    }
}
