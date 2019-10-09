package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.view.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.http.response.FileType.isSupportedFile;

public class FileServlet implements HttpServlet {
    private static final String EXTENSION_DELIMITER = ".";
    private static final int SUBSTRING_INDEX = 1;
    private String fileExtension;
    private Resolver resolver;

    public FileServlet(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean canMapping(HttpRequest httpRequest) {
        String rowAccept = httpRequest.getHeaderValue("Accept");
        if (isExists(rowAccept)) {
            List<String> accepts = parseAccept(rowAccept);
            return isSupportedFile(accepts);
        }
        return false;
    }

    private boolean isExists(String rowAccept) {
        return rowAccept != null;
    }

    private List<String> parseAccept(String rowAccept) {
        return Arrays.asList(rowAccept.split(",")).stream()
                .map(accept -> accept.trim())
                .collect(Collectors.toList());
    }

    @Override
    public ModelAndView run(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        return new ModelAndView(resolver.createView(httpRequest.getAbsPath()));
    }
}
