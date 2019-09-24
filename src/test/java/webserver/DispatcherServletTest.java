package webserver;

import db.DataBase;
import http.*;
import model.User;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.HttpHeader.CONTENT_TYPE;
import static http.HttpRequestTest.LOGIN_REQUEST;
import static http.HttpRequestTest.POST_REQUEST;
import static model.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.IOUtils.convertStringToInputStream;

class DispatcherServletTest {
    static {
        if (!DataBase.findUserById(ID).isPresent()) {
            DataBase.addUser(new User(ID, PASSWORD, NAME, EMAIL));
        }
    }

    @Test
    void static_파일_요청() throws IOException, URISyntaxException {
        String filePath = "/css/styles.css";
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine(filePath, HttpMethod.GET))
                .build();
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeader(CONTENT_TYPE)).isEqualTo(MimeType.of(filePath));
        assertThat(response.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("./static" + filePath));
    }

    @Test
    void 존재하지않는_static_파일_요청() {
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/css/styles.cs", HttpMethod.GET))
                .build();
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void 동적_URL_요청() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(POST_REQUEST));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
    }

    @Test
    void 로그인_요청() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(String.format(LOGIN_REQUEST, ID, PASSWORD)));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
        assertThat(response.getHeader("Set-Cookie")).contains("logined=true");
    }

    @Test
    void 로그인_존재하지않는_유저_Redirect_처리() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(String.format(LOGIN_REQUEST, "ABC", PASSWORD)));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(response.getHeader("Set-Cookie")).contains("logined=false");
    }

    @Test
    void 로그인_비밀번호_에러시_Redirect_처리() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(String.format(LOGIN_REQUEST, ID, "ABC")));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/user/login_failed.html");
        assertThat(response.getHeader("Set-Cookie")).contains("logined=false");
    }
}