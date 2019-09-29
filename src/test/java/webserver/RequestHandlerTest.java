package webserver;

import http.session.HttpSession;
import http.session.support.RandomKeyGenerator;
import http.session.support.SessionManager;
import http.support.HttpCookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static testhelper.Common.getControllerFinder;

public class RequestHandlerTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerTest.class);

    private SessionManager sessionManager;

    @BeforeEach
    public void setUp() {
        sessionManager = new SessionManager(new RandomKeyGenerator());
    }

    @Test
    @DisplayName("정적 파일 반환")
    public void staticFile() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_GET_CSS.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), sessionManager);
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("로그인 실패")
    public void loginFail() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(
                Common.getInputStream("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), sessionManager);
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("로그아웃 상태에서 GET /user/list 요청")
    public void userListWhenLogout() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_GET_USER_LIST_LOGOUT.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), sessionManager);
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 302 FOUND");
        assertThat(bufferedReader.readLine()).isEqualTo("Location: /index.html");
    }

    @Test
    @DisplayName("로그인 성공 이후 list 페이지 정상 접속")
    public void userListWhenLogin() throws IOException {
        HttpSession httpSession = sessionManager.getSession(
                new HttpCookie("SESSIONID=24f92f0e-eaad-4da6-9f68-cc019ecdfa37;"));
        httpSession.setAttribute("logined", "true");

        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_GET_USER_LIST_LOGIN.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), sessionManager);
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }
}