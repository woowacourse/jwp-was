package web.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.URIUtils;
import web.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Objects;

public class HttpResponse {
    public static final String BAD_REQUEST_ERROR_MESSAGE = "errorMessage : ";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final DataOutputStream dataOutputStream;
    private final HttpHeader httpHeader;
    private ResponseLine responseLine;
    private ResponseBody responseBody;

    public HttpResponse(OutputStream outputStream) {
        dataOutputStream = new DataOutputStream(outputStream);
        httpHeader = new HttpHeader();
    }

    public void addHeader(String key, String value) {
        httpHeader.addHeader(key, value);
    }

    public void ok(String path, String contentType) throws IOException, URISyntaxException {
        String filePath = URIUtils.getFilePath(path);
        responseLine = new ResponseLine(ResponseStatus.OK, HTTP_VERSION);
        addHeader(HttpHeader.CONTENT_TYPE, contentType + ";charset=UTF-8");
        responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(filePath));
        write();
    }

    public void found(String location) {
        responseLine = new ResponseLine(ResponseStatus.FOUND, HTTP_VERSION);
        addHeader(HttpHeader.LOCATION, location);
        write();
    }

    public void badRequest(String message) {
        responseLine = new ResponseLine(ResponseStatus.BAD_REQUEST, HTTP_VERSION);
        String errorMessage = BAD_REQUEST_ERROR_MESSAGE + message;
        responseBody = new ResponseBody(errorMessage.getBytes());
        write();
    }

    private void write() {
        try {
            dataOutputStream.writeBytes(responseLine.toString() + " \r\n");
            dataOutputStream.writeBytes(httpHeader.toString() + "\n");
            if (Objects.nonNull(responseBody)) {
                byte[] body = responseBody.getBody();
                dataOutputStream.write(body, 0, body.length);
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
