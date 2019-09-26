package dev.luffy.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import dev.luffy.http.MimeType;
import dev.luffy.http.request.HttpRequest;
import dev.luffy.utils.FileIoUtils;
import dev.luffy.utils.HttpRequestUtils;

public class HttpResponse {

    private static final String NOT_FOUND_404_FILE_PATH = "./templates/error/404.html";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpResponseLine httpResponseLine;
    private HttpResponseHeader httpResponseHeader;
    private HttpResponseBody httpResponseBody;

    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);

        httpResponseLine = new HttpResponseLine();
        httpResponseHeader = new HttpResponseHeader();
        httpResponseBody = new HttpResponseBody();
    }

    public void ok(HttpRequest httpRequest) {
        MimeType mimeType = MimeType.of(httpRequest.pathExtension());
        String filePath = HttpRequestUtils.filePathBuilder(httpRequest.getPath(), mimeType);

        try {
            responseBuilder(httpRequest, HttpStatus.OK, mimeType, filePath);
            send();

        } catch (IOException | URISyntaxException e) {
            logger.error("OK Error : {}", e.getMessage());
        }
    }

    private void send() throws IOException {
        dos.writeBytes(httpResponseLine.combine());
        dos.writeBytes(httpResponseHeader.combine());
        writeBody();
        dos.flush();
    }

    private void writeBody() throws IOException {
        if (httpResponseBody.getBodyLength() != 0) {
            dos.writeBytes("\r\n");
            dos.write(httpResponseBody.getBody(), 0, httpResponseBody.getBodyLength());
        }
    }

    private void responseBuilder(HttpRequest httpRequest,
                                 HttpStatus httpStatus,
                                 MimeType mimeType,
                                 String filePath) throws IOException, URISyntaxException {
        httpResponseBody = new HttpResponseBody(FileIoUtils.loadFileFromClasspath(filePath));

        httpResponseHeader.addHeader("Content-Type", mimeType.getMimeType() + ";charset=utf-8");
        httpResponseHeader.addHeader("Content-Length", String.valueOf(httpResponseBody.getBodyLength()));

        httpResponseLine = new HttpResponseLine(httpRequest.getProtocol(), httpStatus);
    }

    public void redirect(HttpRequest request, String location) {
        httpResponseHeader.addHeader("Location", location);

        httpResponseLine = new HttpResponseLine(request.getProtocol(), HttpStatus.FOUND);
        try {
            send();
        } catch (IOException e) {
            logger.error("Redirect Error : {}", e.getMessage());
        }
    }

    public void notFound(HttpRequest httpRequest) {
        try {
            responseBuilder(httpRequest, HttpStatus.NOT_FOUND, MimeType.HTML, NOT_FOUND_404_FILE_PATH);
            send();
        } catch (IOException | URISyntaxException e) {
            logger.error("NotFound Error : {}", e.getMessage());
        }
    }

    public void addHeader(String key, String value) {
        httpResponseHeader.addHeader(key, value);
    }
}
