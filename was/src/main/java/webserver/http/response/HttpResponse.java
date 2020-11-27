package webserver.http.response;

import static utils.FileIoUtils.*;
import static webserver.http.HttpHeaderFields.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.HttpHeaderFields;
import webserver.http.HttpVersion;
import webserver.http.request.HttpHeader;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String NEW_LINE = "\r\n";
    private static final String SP = " ";
    private static final String COLON = ":";
    private static final String LOCATION = "Location";
    private static final String BASE_URI = "http://localhost:8080";
    private static final String SEME_COLON = ";";
    private static final String PATH = "Path=";

    private final HttpHeader header;
    private final DataOutputStream dos;

    public HttpResponse(HttpHeader header, DataOutputStream dos) {
        this.header = header;
        this.dos = dos;
    }

    public void ok(String classPath, String contentType) {
        try {
            byte[] body = loadFileFromClasspath(classPath);
            proceedOK(body, contentType);
        } catch (NullPointerException e) {
            notFound();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void ok(byte[] body, String contentType) {
        try {
            proceedOK(body, contentType);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void proceedOK(byte[] body, String contentType) throws IOException {
        dos.writeBytes(HttpVersion.HTTP_1_1.getVersion() + SP + HttpStatusCode.OK.getValue()
                + NEW_LINE);
        dos.writeBytes(HttpHeaderFields.CONTENT_TYPE + COLON + SP + contentType + NEW_LINE);
        dos.writeBytes(
                HttpHeaderFields.CONTENT_LENGTH + COLON + SP + body.length + NEW_LINE);
        writeSetCookieIfPresent();
        dos.writeBytes(NEW_LINE);
        dos.write(body, 0, body.length);
        dos.flush();
    }

    private void notFound() {
        try {
            dos.writeBytes(
                    HttpVersion.HTTP_1_1.getVersion() + SP
                            + HttpStatusCode.NOT_FOUND.getValue()
                            + NEW_LINE);
            writeSetCookieIfPresent();
            dos.writeBytes(NEW_LINE);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void redirect(String uri) {
        try {
            dos.writeBytes(
                    HttpVersion.HTTP_1_1.getVersion() + SP + HttpStatusCode.FOUND.getValue()
                            + NEW_LINE);
            dos.writeBytes(LOCATION + COLON + SP + BASE_URI + uri + SP + NEW_LINE);
            writeSetCookieIfPresent();
            dos.writeBytes(NEW_LINE);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void badRequest() {
        try {
            dos.writeBytes(
                    HttpVersion.HTTP_1_1.getVersion() + SP
                            + HttpStatusCode.BAD_REQUEST.getValue()
                            + NEW_LINE);
            writeSetCookieIfPresent();
            dos.writeBytes(NEW_LINE);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void setCookie(String cookie, String path) {
        header.put(SET_COOKIE, cookie + SEME_COLON + SP + PATH + path + SEME_COLON);
    }

    public HttpHeader getHeader() {
        return header;
    }

    private void writeSetCookieIfPresent() throws IOException {
        if (header.containsKey(SET_COOKIE)) {
            dos.writeBytes(
                    SET_COOKIE + COLON + SP + header.get(SET_COOKIE) + NEW_LINE);
        }
    }
}
