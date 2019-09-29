package webserver.controller;

import utils.FileIoUtils;
import webserver.controller.request.HttpRequest;
import webserver.controller.response.HttpResponse;

import java.util.Optional;

public class IndexController extends AbstractController {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        String path = NON_STATIC_FILE_PATH + httpRequest.getPath();
        Optional<byte []> maybeBody = FileIoUtils.loadFileFromClasspath(path);

        return HttpResponse.ok(httpRequest,maybeBody.get());
    }
}
