package webserver.servlet;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.FileView;
import webserver.view.View;

import static utils.HttpRequestUtils.generateTemplateFilePath;

public class HomeServlet extends RequestServlet {
    private static final String INDEX_HTML = "index.html";

    @Override
    public View doGet(HttpRequest request, HttpResponse response) {
        String filePath = generateTemplateFilePath(request.getAbsPath() + INDEX_HTML);
        return new FileView(filePath);
    }
}
