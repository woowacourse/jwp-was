package webserver;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {
    private RequestHandler requestHandler;
    private Socket socket;
    private InetAddress mockInetAddress;
    private OutputStream outputStream;

    @BeforeEach
    void setUp() throws IOException {
        socket = mock(Socket.class);
        mockInetAddress = mock(InetAddress.class);
        outputStream = mock(DataOutputStream.class);
        requestHandler = new RequestHandler(socket);
        when(socket.getInetAddress()).thenReturn(mockInetAddress);
        when(socket.getPort()).thenReturn(8080);
        when(socket.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    void html_확인() throws IOException {
        String request = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";

        when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(request, "UTF-8"));
        requestHandler.run();
    }

    @Test
    void css_확인() throws IOException {
        String request = "GET /css/styles.css HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Accept: text/css,*/*;q=0.1\n" +
                "Connection: keep-alive\n";
        when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(request, "UTF-8"));
        requestHandler.run();
    }

    @Test
    void get_회원가입요청() throws IOException {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(request, "UTF-8"));
        requestHandler.run();
    }

    @Test
    void post_회원가입요청() throws IOException {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(request, "UTF-8"));
        requestHandler.run();
    }

}
