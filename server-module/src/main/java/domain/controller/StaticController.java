package domain.controller;

import domain.request.HttpRequest;
import domain.response.HttpResponse;
import utils.FilePrefixPathMapper;
import utils.StaticFileType;

public class StaticController extends AbstractController {

    private StaticController() {
        super();
    }

    public static StaticController getInstance() {
        return ControllerCache.STATIC_CONTROLLER;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        StaticFileType staticFileType = httpRequest.findExtension();

        String filePath = filePrefixPathMapper.addPrefix(httpRequest.getPath(), staticFileType);

        httpResponse.forward(filePath, staticFileType);
    }

    private static class ControllerCache {

        private static final StaticController STATIC_CONTROLLER = new StaticController();
    }
}
