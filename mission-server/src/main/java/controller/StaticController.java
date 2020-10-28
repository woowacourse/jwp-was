package controller;

import application.AbstractController;
import common.FilePrefixPathMapper;
import servlet.HttpRequest;
import servlet.HttpResponse;
import servlet.StaticFileType;

public class StaticController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        StaticFileType staticFileType = httpRequest.findExtension();

        String filePath = filePrefixPathMapper.addPrefix(httpRequest.getPath(), staticFileType);

        httpResponse.forward(filePath, staticFileType);
    }
}
