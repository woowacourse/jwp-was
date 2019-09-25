package webserver.servlet;

import utils.FileIoUtils;
import utils.HttpRequestUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.ResponseHeader;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileServlet implements HttpServlet {
    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String filePath = generateFilePath(httpRequest);
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        ResponseHeader header = new ResponseHeader();
        header.setContentLegthAndType(body.length, FileIoUtils.loadMIMEFromClasspath(filePath));
        return HttpResponse.ok(header, body);
    }

    private String generateFilePath(HttpRequest httpRequest) {
        if (httpRequest.isAcceptContainsHtml()) {
            return HttpRequestUtils.generateTemplateFilePath(httpRequest.getAbsPath());
        }
        return HttpRequestUtils.generateStaticFilePath(httpRequest.getAbsPath());
    }
}
