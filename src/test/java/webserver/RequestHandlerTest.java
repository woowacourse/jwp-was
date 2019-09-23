package webserver;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestHandlerTest {
	private String fileDirectory = "./src/test/resources/";
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

	@DisplayName("Get 요청 시 정상 작동")
	@Test
	void requestGet() throws IOException {
		when(socket.getInputStream()).thenReturn(new FileInputStream(fileDirectory + "request_GET.txt"));
		requestHandler.run();
	}

	@DisplayName("Post 요청 시 정상 작동")
	@Test
	void requestPost() throws IOException {
		when(socket.getInputStream()).thenReturn(new FileInputStream(fileDirectory + "request_POST.txt"));
		requestHandler.run();
	}
}