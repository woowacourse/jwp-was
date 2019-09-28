package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;

import static webserver.http.response.FileType.isSupportedFile;

public class FileServlet implements HttpServlet {
    public static final String EXTENSION_DELIMITER = ".";
    public static final int SUBSTRING_INDEX = 1;
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
        return url.substring(url.lastIndexOf(EXTENSION_DELIMITER) + SUBSTRING_INDEX);
    }

    @Override
    public ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return new ModelAndView(resolver.createView(httpRequest.getAbsPath()));
    }
}
