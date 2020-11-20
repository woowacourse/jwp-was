package controller;

import domain.controller.AbstractController;
import domain.request.HttpRequest;
import domain.response.HttpResponse;
import utils.FilePrefixPathMapper;
import utils.StaticFileType;

public class RootController extends AbstractController {

    private RootController() {
        super();
    }

    public static RootController getInstance() {
        return ControllerCache.ROOT_CONTROLLER;
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix("/index.html", StaticFileType.HTML);

        httpResponse.forward(filePath, StaticFileType.HTML);
    }

    private static class ControllerCache {

        private static final RootController ROOT_CONTROLLER = new RootController();
    }
}
