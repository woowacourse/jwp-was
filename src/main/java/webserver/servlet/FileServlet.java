package webserver.servlet;

import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class FileServlet implements HttpServlet  {
    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException {
        int statusCode = 200;
        Map<String, Object> header = new HashMap<>();
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getFilePath());
        header.put("lengthOfBodyContent", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(httpRequest.getFilePath()));
        return new HttpResponse(statusCode, header, body);
    }
}
