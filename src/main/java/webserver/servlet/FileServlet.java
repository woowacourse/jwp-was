package webserver.servlet;

import utils.HttpRequestUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.FileView;
import webserver.view.View;

public class FileServlet implements HttpServlet {
    @Override
    public View run(HttpRequest request, HttpResponse response) {
        String filePath = generateFilePath(request);
        return new FileView(filePath);
    }

    private String generateFilePath(HttpRequest httpRequest) {
        if (httpRequest.isAcceptContainsHtml()) {
            return HttpRequestUtils.generateTemplateFilePath(httpRequest.getAbsPath());
        }
        return HttpRequestUtils.generateStaticFilePath(httpRequest.getAbsPath());
    }
}
