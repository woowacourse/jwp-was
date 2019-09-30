package webserver;

import db.DataBase;
import http.*;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import static com.google.common.net.HttpHeaders.*;
import static http.HttpRequestTest.*;
import static model.UserTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.IOUtils.convertStringToInputStream;

class DispatcherServletTest {
    static {
        if (!DataBase.findUserById(ID).isPresent()) {
            DataBase.addUser(new User(ID, PASSWORD, NAME, EMAIL));
        }
    }

    private String sessionId;

    @BeforeEach
    void setUp() {
        HttpSession session = SessionManager.createEmptySession();
        session.setAttributes("user", DataBase.findUserById(ID).get());
        sessionId = session.getId();
        SessionManager.addSession(session);
    }

    @Test
    void static_파일_요청() throws IOException, URISyntaxException {
        String filePath = "/css/styles.css";
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine(filePath, HttpMethod.GET))
                .headers(new HttpHeader(Collections.singletonList("Cookie: SESSIONID=" + sessionId)))
                .build();
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeader(CONTENT_TYPE)).isEqualTo(MimeType.of(filePath));
        assertThat(response.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("./static" + filePath));
    }

    @Test
    void 존재하지않는_static_파일_요청() throws IOException {
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/css/styles.cs", HttpMethod.GET))
                .headers(new HttpHeader(Collections.singletonList("Cookie: SESSIONID=" + sessionId)))
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
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @Test
    void 로그인_요청() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(createLoginRequest(ID, PASSWORD)));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/index.html");
        assertThat(response.getHeader(SET_COOKIE)).contains("logined=true");
    }

    @Test
    void 로그인_존재하지않는_유저_Redirect_처리() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(createLoginRequest("ABC", PASSWORD)));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login_failed.html");
        assertThat(response.getHeader(SET_COOKIE)).contains("logined=false");
    }

    @Test
    void 로그인_비밀번호_에러시_Redirect_처리() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(createLoginRequest(ID, "fail")));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login_failed.html");
        assertThat(response.getHeader(SET_COOKIE)).contains("logined=false");
    }


    @Test
    void 로그인시_유저_목록_조회_처리() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(createUserListRequest(true)));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void 비로그인시_유저_목록_조회_처리() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(createUserListRequest(false)));
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader(LOCATION)).isEqualTo("/user/login.html");
    }

    private String createLoginRequest(String id, String password) {
        return String.format(LOGIN_REQUEST, "SESSIONID=" + sessionId, id, password);
    }

    private String createUserListRequest(boolean logined) {
        return String.format(USER_LIST_REQUEST, COOKIE + ": SESSIONID=" + sessionId + "; logined=" + logined + "\n");
    }
}