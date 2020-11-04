package controller;

import exception.FileNotReadableException;
import http.header.HttpCharacterEncoding;
import http.header.HttpHeader;
import http.header.HttpHeaderName;
import http.header.HttpHeaders;
import http.message.HttpRequestMessage;
import http.message.HttpResponseMessage;
import http.request.HttpResourceType;
import http.request.HttpUri;
import http.response.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class ReadResourceController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ReadResourceController.class);

    @Override
    public HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpUri httpUri = httpRequestMessage.getHttpUri();
        byte[] fileBytes;
        try {
            fileBytes = httpUri.readFile();
        } catch (FileNotReadableException e) {
            return createNotFoundResourceHttpResponseMessage();
        }

        HttpHeader contentType = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getName(),
                                               httpUri.getContentType()
                                                       + HttpHeaders.HEADER_VALUE_DELIMITER
                                                       + HttpCharacterEncoding.UTF_8.toHttpMessage());
        HttpHeader contentLength = HttpHeader.of(HttpHeaderName.CONTENT_LENGTH.getName(),
                                                 String.valueOf(fileBytes.length));

        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.addHeader(contentType);
        httpHeaders.addHeader(contentLength);

        String body = new String(fileBytes, 0, fileBytes.length);

        return HttpResponseMessage.of(HttpStatus.OK, httpHeaders, body);
    }

    private HttpResponseMessage createNotFoundResourceHttpResponseMessage() {
        String filePath = "templates/not_found_error.html";
        byte[] fileBytes = new byte[]{};
        try {
            fileBytes = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        HttpHeader contentType = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getName(),
                                               HttpResourceType.HTML.getContentType()
                                                       + HttpHeaders.HEADER_VALUE_DELIMITER
                                                       + HttpCharacterEncoding.UTF_8.toHttpMessage());
        HttpHeader contentLength = HttpHeader.of(HttpHeaderName.CONTENT_LENGTH.getName(),
                                                 String.valueOf(fileBytes.length));

        HttpHeaders httpHeaders = HttpHeaders.empty();
        httpHeaders.addHeader(contentType);
        httpHeaders.addHeader(contentLength);

        String body = new String(fileBytes, 0, fileBytes.length);

        return HttpResponseMessage.of(HttpStatus.NOT_FOUND, httpHeaders, body);
    }
}
