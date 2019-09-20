package http.response;

import http.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractResponse implements Response {
    private static final Logger log = LoggerFactory.getLogger(AbstractResponse.class);
    private final Request request;
    protected String contentType;

    AbstractResponse(Request request) {
        this.request = request;
    }

    private void response200Header(DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(request.getRequestPath().getPath());
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(this.contentType + "\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void doResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        response200Header(dos);
    }
}
