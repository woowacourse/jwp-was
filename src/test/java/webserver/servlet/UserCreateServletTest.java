package webserver.servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpVersion;
import webserver.resolver.HtmlViewResolver;
import webserver.view.ModelAndView;
import webserver.view.RedirectView;

import java.io.DataOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServletTest extends AbstractServletTest {
    @BeforeEach
    void setup() {
        httpResponse = new HttpResponse(new DataOutputStream(null), HttpVersion.HTTP1);
        resolver = new HtmlViewResolver();
        httpServlet = new UserCreateServlet(resolver);
    }

    @DisplayName("유저 저장")
    @Test
    void doPost_userInformation_equalUserData() throws IOException {
        HttpRequest httpRequest = getCreateUserPostRequest();
        httpServlet.doPost(httpRequest, httpResponse);
        User savedUser = DataBase.findUserById(dummyUser.getUserId());
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo(dummyUser.getUserId());
        assertThat(savedUser.getEmail()).isEqualTo(dummyUser.getEmail());
        assertThat(savedUser.getName()).isEqualTo(dummyUser.getName());
        assertThat(savedUser.getPassword()).isEqualTo(dummyUser.getPassword());
    }
    @DisplayName("유저 저장 후 로그인 페이지로 리다이렉트")
    @Test
    void doPost_addUser_redirect() throws IOException {
        HttpRequest httpRequest = getCreateUserPostRequest();
        ModelAndView modelAndView = httpServlet.doPost(httpRequest, httpResponse);
        assertThat(modelAndView).isEqualTo(new ModelAndView(new RedirectView("/user/login")));
    }
}