package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final Map<String, String> header;

	public HttpResponse(final Map<String, String> header) {
		this.header = header;
	}

	public void sendRedirect(DataOutputStream dos) throws IOException {
		dos.writeBytes(String.format("%s %s %s\r\n", header.get("Http"), header.get("Code")
				, header.get("Description")));
		dos.writeBytes(String.format("Location: %s\r\n", header.get("Location")));
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
