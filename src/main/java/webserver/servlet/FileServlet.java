package webserver.servlet;

import utils.FileIoUtils;
import utils.HttpRequestUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class FileServlet implements HttpServlet {
    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String accept = httpRequest.getHeader("Accept");
        String filePath = generateFilePath(httpRequest.getAbsPath(), accept);
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
        return new HttpResponse(HttpStatus.OK, header, body);
    }

    private String generateFilePath(String absPath, String accept) {
        if (accept.contains("text/html")) {
            return HttpRequestUtils.generateTemplateFilePath(absPath);
        }
        return HttpRequestUtils.generateStaticFilePath(absPath);
    }
}
