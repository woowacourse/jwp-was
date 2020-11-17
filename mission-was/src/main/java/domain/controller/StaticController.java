package domain.controller;

import application.Controller;
import servlet.HttpRequest;
import servlet.HttpResponse;
import servlet.StaticFileType;
import util.FilePrefixPathMapper;

public class StaticController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        FilePrefixPathMapper filePrefixPathMapper = FilePrefixPathMapper.getInstance();
        StaticFileType staticFileType = httpRequest.findExtension();

        String filePath = filePrefixPathMapper.addPrefix(httpRequest.getPath(), staticFileType);

        httpResponse.forward(filePath, staticFileType);
    }
}
