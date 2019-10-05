package view;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.http.HttpStatus;
import webserver.http.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class ViewResolverTest {
    private static final String BODY_NOT_EXIST = "";

    @Test
    void 리다이렉트_뷰리졸버_테스트() throws IOException, URISyntaxException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/user/list");
        modelAndView.setHttpStatus(HttpStatus.REDIRECT);

        ViewResolver.resolve(modelAndView);

        byte[] redirectResponseBody = BODY_NOT_EXIST.getBytes();
        assertThat(modelAndView.getByteView()).isEqualTo(redirectResponseBody);
    }

    @Test
    void 템플릿_뷰리졸버_테스트() throws IOException, URISyntaxException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("./templates/index.html");
        modelAndView.setHttpStatus(HttpStatus.OK);

        ViewResolver.resolve(modelAndView);

        byte[] indexPage = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(modelAndView.getByteView()).isEqualTo(indexPage);
    }

    @Test
    void 탬플릿엔진_뷰리졸버_테스트() throws IOException, URISyntaxException {
        DataBase.addUser(new User("sloth", "password", "sloth", "sloth@sloth.sloth"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("user/list");
        modelAndView.setHttpStatus(HttpStatus.OK);
        modelAndView.setModel("userList", DataBase.findAll());

        ViewResolver.resolve(modelAndView);

        assertThat(new String(modelAndView.getByteView()).contains("sloth")).isTrue();
    }
}