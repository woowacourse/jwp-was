package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final Map<Header, String> header;

	public HttpResponse(final Map<Header, String> header) {
		this.header = header;
	}

	public void sendRedirect(DataOutputStream dos) throws IOException {
		dos.writeBytes(String.format("%s %s %s\r\n", header.get(Header.PROTOCOL), header.get(Header.CODE)
				, header.get(Header.DESCRIPTION)));
		dos.writeBytes(String.format("%s: %s\r\n", Header.LOCATION, header.get(Header.LOCATION)));
		dos.writeBytes("\r\n");
		dos.flush();
	}

	public void forward(byte[] body, DataOutputStream dos) throws IOException {
		for (Header attribute : header.keySet()) {
			dos.writeBytes(attribute.getElement() + ": " + header.get(attribute));
		}
		dos.writeBytes("\r\n");
		sendResponseBody(body, dos);
	}

	private void sendResponseBody(byte[] body, DataOutputStream dos) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
