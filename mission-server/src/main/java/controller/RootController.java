package controller;

import application.AbstractController;
import servlet.HttpRequest;
import servlet.HttpResponse;
import servlet.StaticFileType;
import util.FilePrefixPathMapper;

public class RootController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        String filePath = filePrefixPathMapper.addPrefix("/index.html", StaticFileType.HTML);

        httpResponse.forward(filePath, StaticFileType.HTML);
    }
}
