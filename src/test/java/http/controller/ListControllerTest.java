package http.controller;

import http.model.*;
import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListControllerTest {
    @Test
    void 로그인_된_경우() {
        Controller controller = new ListController();
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, null, httpHeaders);


        User user = new User("coogie123", "password", "coogie", "coogie@gmail.com");
        HttpSession httpSession = httpRequest.getHttpSession();
        httpSession.setAttributes("user", user);
        httpHeaders.addHeader("Cookie", "JSESSIONID=" + httpSession.getId());

        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 로그인_안된_경우() {
        Controller controller = new ListController();
        String requestMessage = "GET /user/list HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, null, httpHeaders);

        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}