package webserver.httpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.HttpResponseProcessor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Http2xxResponseProcessor implements HttpResponseProcessor {
    private static final Logger log = LoggerFactory.getLogger(Http2xxResponseProcessor.class);
    private static final String TAG = "Http2xxResponseProcessor";

    @Override
    public void process(DataOutputStream dos, String resolve, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resolve);
            responseHeader(dos, httpResponse.getContentType(), body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            log.error("{} 클래스에서 {} 메서드 에러 발생", TAG, "process");
        }
    }

    private void responseHeader(DataOutputStream dos, String contentType, int lengthOfBodyContent) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes("Content-Type: " + contentType + "\r\n");
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
