package http.view;

import http.model.ContentType;
import http.model.HttpResponse;

public interface Resolver {
    String EXTENSION_SEPARATOR = ".";

    HttpResponse resolve(ModelAndView modelAndView);

    default ContentType getContentType(ModelAndView modelAndView) {
        String resourceName = modelAndView.getViewLocation();
        String extension = resourceName.substring(resourceName.lastIndexOf(EXTENSION_SEPARATOR) + 1);
        return ContentType.of(extension);
    }
}
