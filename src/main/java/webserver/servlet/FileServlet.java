package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.request.RequestUri;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import static webserver.http.response.FileType.isSupportedFile;

public class FileServlet implements HttpServlet {
    private String fileExtension;

    public FileServlet(Resolver resolver) {
        super(resolver);
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
    public ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) {
        return new ModelAndView())
    }
}
