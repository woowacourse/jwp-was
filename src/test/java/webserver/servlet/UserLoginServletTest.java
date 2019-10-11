package webserver.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.resolver.HtmlViewResolver;
import webserver.view.ForwardView;
import webserver.view.RedirectView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginServletTest extends AbstractServletTest {
    @BeforeEach
    void setup() {
        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        resolver = new HtmlViewResolver();
        httpServlet = new UserLoginServlet(resolver);
    }

    @DisplayName("로그인 페이지 이동")
    @Test
    void doGet_loginForm_forward() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getCommonGetRequest("/");
        assertThat(httpServlet.doGet(httpRequest, httpResponse).getView()).isInstanceOf(ForwardView.class);
    }

    @DisplayName("로그인 실패시 리다이렉트")
    @Test
    void doPost_loginFail_redirect() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getLoginPostRequest(buildUserBody("not","exist"));
        assertThat(httpServlet.doPost(httpRequest, httpResponse).getView()).isInstanceOf(RedirectView.class);
    }

    @DisplayName("로그인 실패시 세션에 유저 없음")
    @Test
    void doPost_loginFail_sessionUserNotExits() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getLoginPostRequest(buildUserBody("not","exist"));
        assertThat(httpRequest.getSession().getAttribute("user")).isNull();
    }

    @DisplayName("로그인 성공시 세션에 유저 추가")
    @Test
    void doPost_loginSuccess_sessionAdd() throws IOException, URISyntaxException {
        signUp(dummyUser);
        HttpRequest httpRequest = getLoginPostRequest(dummyLoginBody());
        httpServlet.doPost(httpRequest, httpResponse);
        assertThat(httpRequest.getSession().getAttribute("user")).isEqualTo(dummyUser);
    }

    String buildUserBody(String id, String password) {
        return "userId=" + id +
                "&password=" + password;
    }

}