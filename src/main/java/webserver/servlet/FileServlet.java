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
    private static FileServlet instance = null;

    private FileServlet() {
    }

    public static FileServlet getInstance() {
        if (instance == null) {
            instance = new FileServlet();
        }
        return instance;
    }

    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String filePath = generateFilePath(httpRequest);
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
        return new HttpResponse(HttpStatus.OK, header, body);
    }

    private String generateFilePath(HttpRequest httpRequest) {
        if (httpRequest.isAcceptContainsHtml()) {
            return HttpRequestUtils.generateTemplateFilePath(httpRequest.getAbsPath());
        }
        return HttpRequestUtils.generateStaticFilePath(httpRequest.getAbsPath());
    }
}
