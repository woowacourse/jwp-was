package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

import com.google.common.net.HttpHeaders;
import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.request.MimeType;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class StaticResourceHandlerMapping implements HandlerMapping {
    @Override
    public boolean isSupport(HttpRequest request) {
        return request.isStaticResourceRequest();
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        if (!request.isGetMethod()) {
            throw new IllegalArgumentException("허용하는 메소드가 아닙니다. 405예외를 던질 예정입니다.");
        }
        final MimeType mime = MimeType.of(request.getPath());
        try {
            final byte[] body = FileIoUtils.loadFileFromClasspath(mime.getFilePosition() + request.getPath());
            response.changeHttpStatus(HttpStatus.OK);
            response.addHeader(HttpHeaders.CONTENT_TYPE, mime.getMimeType());
            response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length));
            response.addBody(body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.out.println("404 테스트");
        }
    }
}
