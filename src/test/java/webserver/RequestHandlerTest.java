package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestHandlerTest {
    private RequestHandler requestHandler;
    private Socket socket;

    @BeforeEach
    void setUp() throws IOException {
        socket = mock(Socket.class);
        InetAddress mockAddress = mock(InetAddress.class);
        OutputStream outputStream = mock(DataOutputStream.class);
        requestHandler = new RequestHandler(socket);

        when(socket.getInetAddress()).thenReturn(mockAddress);
        when(socket.getPort()).thenReturn(8080);
        when(socket.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    void 동작_테스트() throws IOException {
        String header = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 46\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "Cookie: JSESSION=abcd\n" +
                "\n" +
                "userId=javajigi&password=password&name=JaeSung";

        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(header.getBytes()));
        requestHandler.run();
    }
}