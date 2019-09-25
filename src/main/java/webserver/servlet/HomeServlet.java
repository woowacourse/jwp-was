package webserver.servlet;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;
import webserver.response.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

import static utils.HttpRequestUtils.generateTemplateFilePath;

public class HomeServlet extends RequestServlet {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String filePath = generateTemplateFilePath(httpRequest.getAbsPath() + "index.html");
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        ResponseHeader header = new ResponseHeader();
        header.setContentLegthAndType(body.length, FileIoUtils.loadMIMEFromClasspath(filePath));
        return new HttpResponse(HttpStatus.OK, header, body);
    }
}
