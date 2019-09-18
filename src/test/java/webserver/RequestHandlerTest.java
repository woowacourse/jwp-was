package webserver;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {
    private RequestHandler requestHandler;

    @Test
    void InputStream_올바른경우() throws IOException {
        Socket socket = mock(Socket.class);
        String request = "GET /index2.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(socket.getInetAddress()).thenReturn(mockInetAddress);
        when(socket.getPort()).thenReturn(8080);
        when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(request, "UTF-8"));
        requestHandler = new RequestHandler(socket);

        requestHandler.run();
    }

    @Test
    void run_회원가입요청() throws IOException {
        Socket socket = mock(Socket.class);
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        InetAddress mockInetAddress = mock(InetAddress.class);
        when(socket.getInetAddress()).thenReturn(mockInetAddress);
        when(socket.getPort()).thenReturn(8080);
        when(socket.getInputStream()).thenReturn(IOUtils.toInputStream(request, "UTF-8"));
        requestHandler = new RequestHandler(socket);

        requestHandler.run();
    }
}
