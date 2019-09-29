package http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import http.Header;
import http.HeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import static http.response.HttpResponseGenerator.*;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	private final StatusLine statusLine;
	private final Header header;

	public HttpResponse(final StatusLine statusLine, final Header header) {
		this.statusLine = statusLine;
		this.header = header;
	}

	public void sendRedirect(DataOutputStream dos) throws IOException {
		String responseStatusLine = String.format("%s %s %s \r\n", statusLine.getElementValue(HTTP_VERSION)
				, statusLine.getElementValue(STATUS_CODE), statusLine.getElementValue(REASON_PHRASE));
		String responseLocation = String.format("%s: %s\r\n", HeaderElement.LOCATION.getElement(), header.getElementValue(HeaderElement.LOCATION));

		dos.writeBytes(responseStatusLine);
		dos.writeBytes(responseLocation);
		dos.writeBytes("\r\n");
		dos.flush();
	}

	public void forward(byte[] body, DataOutputStream dos) throws IOException {
		String responseStatusLine = String.format("%s %s %s \r\n", statusLine.getElementValue(HTTP_VERSION)
				, statusLine.getElementValue(STATUS_CODE), statusLine.getElementValue(REASON_PHRASE));
		dos.writeBytes(responseStatusLine);

		for (String s : header.printHeader()) {
			dos.writeBytes(s);
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

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public Header getHeader() {
		return header;
	}

	public void setInitialSession(String sessionId) {
		header.setSessionId(sessionId);
	}
}
