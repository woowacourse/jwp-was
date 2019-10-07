package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;

import webserver.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import static webserver.http.response.HttpResponseGenerator.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String HEADER_RESPONSE_LOCATION = "Location";

    private final StatusLine statusLine;
    private final Header header;
    private final byte[] responseBody;

    public HttpResponse(final StatusLine statusLine, final Header header) {
        this(statusLine, header, null);
    }

    public HttpResponse(StatusLine statusLine, Header header, byte[] responseBody) {
        this.statusLine = statusLine;
        this.header = header;
        this.responseBody = responseBody;
    }

    public void sendRedirect(DataOutputStream dos) throws IOException {
        String responseStatusLine = String.format("%s %s %s \r\n", statusLine.getElementValue(HTTP_VERSION)
                , statusLine.getElementValue(STATUS_CODE), statusLine.getElementValue(REASON_PHRASE));
        String responseLocation = String.format("%s: %s\r\n", HEADER_RESPONSE_LOCATION, header.get(HEADER_RESPONSE_LOCATION));

        dos.writeBytes(responseStatusLine);
        dos.writeBytes(responseLocation);
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void forward(DataOutputStream dos) throws IOException {
        String responseStatusLine = String.format("%s %s %s \r\n", statusLine.getElementValue(HTTP_VERSION)
                , statusLine.getElementValue(STATUS_CODE), statusLine.getElementValue(REASON_PHRASE));
        dos.writeBytes(responseStatusLine);

        for (String s : header.printHeader()) {
            dos.writeBytes(s);
        }

        dos.writeBytes("\r\n");
        sendResponseBody(dos);
    }

    private void sendResponseBody(DataOutputStream dos) {
        try {
            dos.write(responseBody, 0, responseBody.length);
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
