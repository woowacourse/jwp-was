package web.application.controller;

import web.application.common.FilePrefixPathMapper;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;
import web.server.utils.StaticFileType;

public class RootController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix("/index.html", StaticFileType.HTML);

        httpResponse.forward(filePath, StaticFileType.HTML);
    }
}
