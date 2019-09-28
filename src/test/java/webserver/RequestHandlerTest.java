package webserver;

import db.DataBase;
import domain.User;
import http.session.support.SessionManager;
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
    
    @Test
    @DisplayName("정적 파일 반환")
    public void staticFile() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_GET_CSS.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), getSessionManager());
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("로그인 실패")
    public void loginFail() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), getSessionManager());
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("로그인 성공 후 Set-Cookie가 설정됬는지 확인")
    public void loginSuccess() throws IOException {
        DataBase.addUser(new User("javajigi", "password", "name", "java@slipp.net"));

        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_POST_USER_LOGIN.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), getSessionManager());
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 302 FOUND");
        assertThat(bufferedReader.readLine()).isEqualTo("Content-Type: text/html");
        assertThat(bufferedReader.readLine()).isEqualTo("Location: /index.html");
        assertThat(bufferedReader.readLine().contains("logined=true")).isTrue();
    }

    @Test
    @DisplayName("로그인 상태에서 /user/list 요청")
    public void userList() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_GET_USER_LIST_LOGIN.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), getSessionManager());
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("세션 설정")
    public void sessionTest() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_SESSION_TEST.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, getControllerFinder(), getSessionManager());
        requestHandler.run();

        BufferedReader bufferedReader = Common.convertToBufferedReader(loginSocket.getOutputStream());
        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(bufferedReader.readLine()).isEqualTo("Content-Length: 5675");
        assertThat(bufferedReader.readLine().contains("SESSIONID=")).isTrue();
    }

    private SessionManager getSessionManager() {
        return new SessionManager();
    }
}