package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.FileType;
import webserver.http.response.HttpResponse;
import webserver.view.View;

import java.io.IOException;

import static webserver.http.response.FileType.isSupportedFile;

public class FileServlet implements HttpServlet {
    String fileExtension;
    @Override
    public boolean canMapping(RequestUri requestUri) {
        fileExtension = parseFileExtension(requestUri.getAbsPath());
        return isSupportedFile(fileExtension);
    }

    private String parseFileExtension(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    @Override
    public View run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        httpResponse.ok();
        httpResponse.appendContentType(FileType.getTypeByExtension(fileExtension));
        return new View(httpRequest.getAbsPath());
    }
}
