package web.application.controller;

import web.application.common.FilePrefixPathMapper;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;
import web.server.utils.StaticFileType;

public class StaticController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        StaticFileType staticFileType = httpRequest.findExtension();

        String filePath = filePrefixPathMapper.addPrefix(httpRequest.getPath(), staticFileType);

        httpResponse.forward(filePath, staticFileType);
    }
}
