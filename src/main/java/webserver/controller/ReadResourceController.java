package webserver.controller;

import exception.FileNotReadableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.body.DefaultHttpBody;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpCharacterEncoding;
import webserver.http.header.HttpHeader;
import webserver.http.header.HttpHeaderField;
import webserver.http.message.HttpMessage;
import webserver.http.message.HttpRequestMessage;
import webserver.http.request.HttpResourceType;
import webserver.http.request.HttpUri;
import webserver.http.response.HttpStatus;
import webserver.http.response.StatusLine;

public class ReadResourceController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(ReadResourceController.class);

    @Override
    public HttpMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage) {
        HttpUri httpUri = httpRequestMessage.getRequestLine().getHttpUri();
        byte[] fileBytes;
        try {
            fileBytes = httpUri.readFile();
        } catch (FileNotReadableException e) {
            return createNotFoundResourceHttpResponseMessage();
        }

        StatusLine statusLine = new StatusLine(HttpStatus.OK);
        HttpHeader httpHeader = new HttpHeader.Builder()
                .addHeader(HttpHeaderField.CONTENT_TYPE.getName(),
                           httpUri.getContentType() + HttpCharacterEncoding.UTF_8.toHttpMessage())
                .addHeader(HttpHeaderField.CONTENT_LENGTH.getName(), String.valueOf(fileBytes.length))
                .build();
        HttpBody httpbody = DefaultHttpBody.from(new String(fileBytes, 0, fileBytes.length));

        return new HttpMessage(statusLine, httpHeader, httpbody);
    }

    private HttpMessage createNotFoundResourceHttpResponseMessage() {
        String filePath = "./templates/not_found_error.html";
        byte[] fileBytes = new byte[]{};
        try {
            fileBytes = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        StatusLine statusLine = new StatusLine(HttpStatus.NOT_FOUND);
        HttpHeader httpHeader = new HttpHeader.Builder()
                .addHeader(HttpHeaderField.CONTENT_TYPE.getName(),
                           HttpResourceType.HTML.getContentType() + HttpCharacterEncoding.UTF_8.toHttpMessage())
                .addHeader(HttpHeaderField.CONTENT_LENGTH.getName(), String.valueOf(fileBytes.length))
                .build();
        HttpBody httpbody = DefaultHttpBody.from(new String(fileBytes, 0, fileBytes.length));

        return new HttpMessage(statusLine, httpHeader, httpbody);
    }
}
