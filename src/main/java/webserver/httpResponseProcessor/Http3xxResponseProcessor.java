package webserver.httpResponseProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpResponse;
import webserver.HttpResponseProcessor;

import java.io.DataOutputStream;
import java.io.IOException;

public class Http3xxResponseProcessor implements HttpResponseProcessor {
    private static final Logger log = LoggerFactory.getLogger(Http3xxResponseProcessor.class);
    private static final String TAG = "Http3xxResponseProcessor";

    @Override
    public void process(DataOutputStream dos, String resolve, HttpResponse httpResponse) {
        response302Header(dos, "localhost:8080", resolve);
    }

    private void response302Header(DataOutputStream dos, String host, String path) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: http://" + host + path + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error("{} 클래스에서 에러 발생", TAG);
        }
    }
}
