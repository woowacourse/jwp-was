package webserver.http.response;

import static utils.FileIoUtils.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpHeaderFields;
import webserver.http.request.HttpVersion;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = "\r\n";
    private static final String SP = " ";
    private static final String COLON = ":";
    private static final String LOCATION = "Location";
    private static final String BASE_URI = "http://localhost:8080";

    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void ok(String classPath, String contentType) {
        try {
            try {
                byte[] body = loadFileFromClasspath(classPath);
                dos.writeBytes(HttpVersion.HTTP_1_1.getVersion() + SP + HttpStatusCode.OK.getValue()
                        + NEW_LINE);
                dos.writeBytes(HttpHeaderFields.CONTENT_TYPE + COLON + SP + contentType + NEW_LINE);
                dos.writeBytes(
                        HttpHeaderFields.CONTENT_LENGTH + COLON + SP + body.length + NEW_LINE);
                dos.writeBytes(NEW_LINE);
                dos.write(body, 0, body.length);
                dos.flush();
            } catch (NullPointerException e) {
                notFound();
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void notFound() {
        try {
            dos.writeBytes(
                    HttpVersion.HTTP_1_1.getVersion() + SP + HttpStatusCode.NOT_FOUND.getValue()
                            + NEW_LINE);
            dos.writeBytes(NEW_LINE);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void redirect(String uri) {
        try {
            dos.writeBytes(HttpVersion.HTTP_1_1.getVersion() + SP + HttpStatusCode.FOUND.getValue()
                    + NEW_LINE);
            dos.writeBytes(LOCATION + COLON + SP + BASE_URI + uri + SP + NEW_LINE);
            dos.writeBytes(NEW_LINE);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void badRequest() {
        try {
            dos.writeBytes(
                    HttpVersion.HTTP_1_1.getVersion() + SP + HttpStatusCode.BAD_REQUEST.getValue()
                            + NEW_LINE);
            dos.writeBytes(NEW_LINE);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
