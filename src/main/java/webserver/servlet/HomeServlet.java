package webserver.servlet;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static utils.HttpRequestUtils.generateTemplateFilePath;

public class HomeServlet extends RequestServlet {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        String filePath = generateTemplateFilePath(httpRequest.getAbsPath() + "index.html");
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
        return new HttpResponse(HttpStatus.OK, header, body);
    }
}
