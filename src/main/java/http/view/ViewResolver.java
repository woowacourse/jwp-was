package http.view;

import http.model.ContentType;
import http.model.HttpProtocols;
import http.model.HttpResponse;
import http.model.HttpStatus;
import utils.FileIoUtils;

public class ViewResolver implements Resolver {
    private static final String HTML_PATH = "./templates";
    private static final String STATIC_PATH ="./static";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String EXTENSION_SEPARATOR = ".";


    @Override
    public HttpResponse resolve(ModelAndView modelAndView) {
        String resource = modelAndView.getViewLocation();
        if (resource.startsWith(REDIRECT_PREFIX)) {
            return new HttpResponse(HttpProtocols.DEFAULT, HttpStatus.FOUND, ContentType.HTML);
        }
        String resourceName = modelAndView.getViewLocation();
        String extension = resourceName.substring(resourceName.lastIndexOf(EXTENSION_SEPARATOR) + 1);
        ContentType contentType = ContentType.of(extension);
        if (contentType == ContentType.HTML) {
            return new HttpResponse(HttpProtocols.DEFAULT, HttpStatus.OK, contentType, FileIoUtils.loadFileFromClasspath(HTML_PATH + modelAndView.getViewLocation()));
        }
        return new HttpResponse(HttpProtocols.DEFAULT, HttpStatus.OK, contentType, FileIoUtils.loadFileFromClasspath(STATIC_PATH + modelAndView.getViewLocation()));
    }
}
