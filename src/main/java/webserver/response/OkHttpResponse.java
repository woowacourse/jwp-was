package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class OkHttpResponse extends HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(OkHttpResponse.class);

    public OkHttpResponse(OutputStream out) {
        super(out);
    }

    @Override
    public void makeResponse(final HttpRequest httpRequest) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.findFilePath());
        response200Header(dos, httpRequest.findContentType(), body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
