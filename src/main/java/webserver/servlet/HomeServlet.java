package webserver.servlet;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

import static utils.HttpRequestUtils.generateTemplateFilePath;

public class HomeServlet extends RequestServlet {
    private static final String INDEX_HTML = "index.html";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String filePath = generateTemplateFilePath(httpRequest.getAbsPath() + INDEX_HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        ResponseHeader header = new ResponseHeader();
        header.setContentLengthAndType(body.length, FileIoUtils.loadMIMEFromClasspath(filePath));
        return HttpResponse.ok(header, body);
    }
}
