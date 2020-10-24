package webserver.http.service;

import static utils.FileIoUtils.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpBody;
import webserver.http.request.HttpHeader;
import webserver.http.request.HttpHeaderFields;
import webserver.http.request.HttpVersion;
import webserver.http.request.RequestURI;

public class HttpGetService extends AbstractHttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpGetService.class);

    public HttpGetService(HttpHeader header, HttpBody body) {
        super(header, body);
    }

    @Override
    public void doService(OutputStream out) throws URISyntaxException, IOException {
        DataOutputStream dos = new DataOutputStream(out);
        RequestURI uri = header.getRequestURI();

        byte[] body = loadFileFromClasspath(uri.getClassPath());
        response200Header(dos, body.length, uri.getContentType());
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfContent, String contentType) {
        try {
            dos.writeBytes(HttpVersion.HTTP_1_1.getVersion() + " 200 OK \r\n");
            dos.writeBytes(HttpHeaderFields.CONTENT_TYPE + ": " + contentType + "\r\n");
            dos.writeBytes(HttpHeaderFields.CONTENT_LENGTH + ": " + lengthOfContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
