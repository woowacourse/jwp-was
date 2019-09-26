package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import model.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;
import webserver.controller.Controller;
import webserver.controller.ControllerFinder;
import webserver.controller.CreateUserController;
import webserver.controller.LoginUserController;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerTest.class);
    private static Map<String, Controller> api;

    static {
        api = new HashMap<>();
        api.put("/user/create", new CreateUserController());
        api.put("/user/login", new LoginUserController());

        try {
            HttpRequest httpRequest =
                    HttpRequestFactory.create(Common.getBufferedReader("HTTP_POST_USER_CREATE.txt"));
            new UserController().addUser(httpRequest);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    ControllerFinder controllerFinder = new ControllerFinder(Collections.unmodifiableMap(api));

    @Test
    @DisplayName("로그인 실패")
    public void loginFail() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_POST_USER_LOGIN_FAIL_PASSWORD.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, controllerFinder);
        requestHandler.run();

        InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) loginSocket.getOutputStream()).toByteArray());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("로그인 성공 후 Set-Cookie가 설정됬는지 확인")
    public void loginSuccess() throws IOException {
        Socket loginSocket = mock(Socket.class);
        when(loginSocket.getInputStream()).thenReturn(Common.getInputStream("HTTP_POST_USER_LOGIN.txt"));
        when(loginSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        RequestHandler requestHandler = new RequestHandler(loginSocket, controllerFinder);
        requestHandler.run();

        InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) loginSocket.getOutputStream()).toByteArray());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        logger.info("\n" + loginSocket.getOutputStream().toString());
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 302 FOUND");
        assertThat(bufferedReader.readLine()).isEqualTo("Set-Cookie: logined=true; Path=/");
        assertThat(bufferedReader.readLine()).isEqualTo("Location: /index.html");
    }
}