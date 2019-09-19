package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public abstract class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    public abstract void makeResponse(OutputStream out, HttpRequest request) throws IOException, URISyntaxException;

    protected void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
