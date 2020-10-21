package client.controller;

import utils.FileIoUtils;
import utils.ContentTypeMatcher;
import web.HeaderName;
import web.controller.Controller;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.response.HttpStatusCode;

import java.io.IOException;

public class ResourceController implements Controller {
    private final String filePath;

    public ResourceController(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestPath = httpRequest.getRequestPath();

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(this.filePath);
            httpResponse.putHeader(HeaderName.CONTENT_TYPE.getName(), ContentTypeMatcher.findResourceContentType(requestPath));
            httpResponse.response(HttpStatusCode.OK, body);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s : 해당경로에 파일이 존재하지 않습니다.", requestPath));
        }

    }
}
