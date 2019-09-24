package webserver.servlet;

import utils.FileIoUtils;
import utils.HttpRequestUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileServlet implements HttpServlet {
    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException {
        String filePath = generateFilePath(httpRequest.getAbsPath(), httpRequest.isHeaderContain("Accept", "text/html"));
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Length", body.length);
        header.put("Content-Type", FileIoUtils.loadMIMEFromClasspath(filePath));
        return HttpResponse.ok(header, body);
    }

    private String generateFilePath(String absPath, boolean isHtml) {
        if (isHtml) {
            return HttpRequestUtils.generateTemplateFilePath(absPath);
        }
        return HttpRequestUtils.generateStaticFilePath(absPath);
    }
}
