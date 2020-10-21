package client.controller;

import utils.FileIoUtils;
import web.HeaderName;
import web.controller.Controller;
import web.request.HttpRequest;
import web.response.HttpResponse;
import web.response.HttpStatusCode;
import utils.ResourceMatcher;

import java.io.IOException;

public class ResourceController implements Controller {
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestPath = httpRequest.getRequestPath();
        ResourceMatcher matcher = ResourceMatcher.findResourceMatcher(requestPath);

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(matcher.getResourcePath() + requestPath);
            httpResponse.putHeader(HeaderName.CONTENT_TYPE.getName(), matcher.getContentType());
            httpResponse.response(HttpStatusCode.OK, body);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("%s : 해당경로에 파일이 존재하지 않습니다.", requestPath));
        }

    }
}
