package web.application.controller;

import web.application.common.FilePrefixPathMapper;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;
import web.server.utils.StaticFileType;

public class StaticController extends AbstractController {

    private StaticController() {
        super();
    }

    public static StaticController getInstance() {
        return ControllerCache.staticController;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        StaticFileType staticFileType = httpRequest.findExtension();

        String filePath = filePrefixPathMapper.addPrefix(httpRequest.getPath(), staticFileType);

        httpResponse.forward(filePath, staticFileType);
    }

    private static class ControllerCache {
        private static final StaticController staticController = new StaticController();
    }
}
