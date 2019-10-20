package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpSessionHandler;
import webserver.common.HttpSession;
import webserver.common.HttpStatus;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static controller.support.Constants.LOGINED_KEY;
import static controller.support.Constants.LOGINED_VALUE_TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static webserver.support.ConStants.*;

public class UserListControllerTest {
    private static final String LOGIN_FALSE_GET_REQUEST_MESSAGE =
            "GET /user/list.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: text/html\n" +
                    "Cookie: logined=false";

    private static final String LOGIN_TRUE_GET_REQUEST_MESSAGE =
            "GET /user/list.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: text/html\n" +
                    "Cookie: logined=true; JSESSIONID=uuid";

    @DisplayName("Cookie 필드의 값이 logined=false 인 경우 /user/list GET 요청")
    @Test
    void doGet_login_false() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(LOGIN_FALSE_GET_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new UserListController();
        controller.service(httpRequest, httpResponse);

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.setStatusLine(httpRequest, HttpStatus.FOUND);
        httpResponseToCompare.setHeader(HEADER_FIELD_LOCATION, "http://localhost:8080/user/login.html");

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }

    @DisplayName("Cookie 필드의 값이 logined=true 인 경우 /user/list GET 요청")
    @Test
    void doGet_login_true() throws IOException {
        DataBase.removeAll();
        String uuid = "uuid";
        HttpSession session = HttpSessionHandler.createHttpSession(uuid);
        session.setAttribute(LOGINED_KEY, LOGINED_VALUE_TRUE);
        session.setAttribute("Path", "/");

        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(LOGIN_TRUE_GET_REQUEST_MESSAGE.getBytes()));
        HttpResponse httpResponse = new HttpResponse();
        Controller controller = new UserListController();
        controller.service(httpRequest, httpResponse);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile("/user/list");

        Map<String, Object> model = new HashMap<>();
        byte[] file = template.apply(model).getBytes();

        HttpResponse httpResponseToCompare = new HttpResponse();
        httpResponseToCompare.setStatusLine(httpRequest, HttpStatus.OK);
        httpResponseToCompare.setHeader(HEADER_FIELD_CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponseToCompare.setHeader(HEADER_FIELD_CONTENT_LENGTH, "4356");
        httpResponseToCompare.setBody(file);

        assertThat(httpResponse).isEqualTo(httpResponseToCompare);
    }
}
