package webserver.controller;

import exception.FileNotReadableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.header.HttpCharacterEncoding;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;
import webserver.http.request.HttpResourceType;
import webserver.http.request.HttpUri;
import webserver.http.response.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ReadResourceController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ReadResourceController.class);

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpUri httpUri = httpRequestMessage.getRequestLine().getHttpUri();
        byte[] fileBytes;
        try {
            fileBytes = httpUri.readFile();
        } catch (FileNotReadableException e) {
            return createNotFoundResourceHttpResponseMessage();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.CONTENT_TYPE.getName(),
                    httpUri.getContentType() + HttpCharacterEncoding.UTF_8.toHttpMessage());
        headers.put(HttpHeaderField.CONTENT_LENGTH.getName(), String.valueOf(fileBytes.length));

        String body = new String(fileBytes, 0, fileBytes.length);

        return HttpResponseMessage.of(HttpStatus.OK, headers, body);
    }

    private HttpResponseMessage createNotFoundResourceHttpResponseMessage() {
        String filePath = "./templates/not_found_error.html";
        byte[] fileBytes = new byte[]{};
        try {
            fileBytes = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaderField.CONTENT_TYPE.getName(),
                    HttpResourceType.HTML.getContentType() + HttpCharacterEncoding.UTF_8.toHttpMessage());
        headers.put(HttpHeaderField.CONTENT_LENGTH.getName(), String.valueOf(fileBytes.length));

        String body = new String(fileBytes, 0, fileBytes.length);

        return HttpResponseMessage.of(HttpStatus.NOT_FOUND, headers, body);
    }
}
