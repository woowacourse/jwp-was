package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class FoundHttpResponse extends HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(FoundHttpResponse.class);
    private final String location;

    public FoundHttpResponse(final String location) {
        this.location = location;
    }

    @Override
    public void makeResponse(final OutputStream out, final HttpRequest request) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        response302Header(dos, location);
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location:" + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
