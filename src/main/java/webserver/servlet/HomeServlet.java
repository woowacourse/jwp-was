package webserver.servlet;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HomeServlet extends RequestServlet {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        Map<String, Object> header = new HashMap<>();
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getFilePath());
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(httpRequest.getFilePath()));
        return new HttpResponse(HttpStatus.OK, header, body);
    }
}
