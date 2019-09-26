package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import static http.response.HttpResponseGenerator.*;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final Map<String, String> header;

	public HttpResponse(final Map<String, String> header) {
		this.header = header;
	}

	public void sendRedirect(DataOutputStream dos) throws IOException {
		dos.writeBytes(String.format("%s %s %s\r\n", header.get(RESPONSE_PROTOCOL), header.get(RESPONSE_CODE)
				, header.get(RESPONSE_DESCRIPTION)));
		dos.writeBytes(String.format("%s: %s\r\n", RESPONSE_LOCATION, header.get(RESPONSE_LOCATION)));
		dos.writeBytes("\r\n");
		dos.flush();
	}

	public void forward(byte[] body, DataOutputStream dos) throws IOException {
		for (String key : header.keySet()) {
			dos.writeBytes(key + ": " + header.get(key));
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
