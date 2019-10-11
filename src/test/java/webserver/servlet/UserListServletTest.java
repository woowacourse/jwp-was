package webserver.servlet;

import db.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.resolver.HandlebarViewResolver;
import webserver.view.HandlebarView;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListServletTest extends AbstractServletTest {
    @BeforeEach
    void setup() {
        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        resolver = new HandlebarViewResolver();
        httpServlet = new UserListServlet(resolver);
        signUp(dummyUser);
    }

    @DisplayName("비로그인 시 리다이렉트")
    @Test
    void doGet_notLogin_redirect() throws IOException, URISyntaxException {
        HttpRequest httpRequest = getUserListRequest(null);
        assertThat(httpServlet.doGet(httpRequest, httpResponse).getView()).isInstanceOf(RedirectView.class);
    }

    @DisplayName("로그인 시 포워드 뷰")
    @Test
    void doGet_loggedIn_foward() throws IOException, URISyntaxException {
        login(dummyUser, dummySessionID);
        HttpRequest httpRequest = getUserListRequest(dummySessionID);
        assertThat(httpServlet.doGet(httpRequest, httpResponse).getView()).isInstanceOf(HandlebarView.class);
    }

    @DisplayName("성공시 가입자 정보 확인")
    @Test
    void doGet_loggedIn_userData() throws IOException, URISyntaxException {
        login(dummyUser, dummySessionID);
        HttpRequest httpRequest = getUserListRequest(dummySessionID);
        ModelAndView mv = httpServlet.doGet(httpRequest, httpResponse);
        mv.getModelMap().get(dummyUser);
        assertThat(mv.getModelMap().get("users")).isEqualTo(DataBase.findAll());
    }
}