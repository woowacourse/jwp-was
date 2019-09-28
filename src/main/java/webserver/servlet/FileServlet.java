package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;

import static webserver.http.response.FileType.isSupportedFile;

public class FileServlet implements HttpServlet {
    private String fileExtension;
    private Resolver resolver;

    public FileServlet(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean canMapping(RequestUri requestUri) {
        fileExtension = parseFileExtension(requestUri.getAbsPath());
        return isSupportedFile(fileExtension);
    }

    private String parseFileExtension(String url) {
        return url.substring(url.lastIndexOf(".") + 1);
    }

    @Override
    public ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return new ModelAndView(resolver.createView(httpRequest.getAbsPath()));
    }
}
