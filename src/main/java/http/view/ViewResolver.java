package http.view;

import http.model.*;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;

public class ViewResolver implements Resolver {
    private static final String HTML_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String EXTENSION_SEPARATOR = ".";
    private static final String CONTENT_TYPE = "Content-Type";


    @Override
    public HttpResponse resolve(ModelAndView modelAndView) {
        String resource = modelAndView.getViewLocation();
        HttpHeaders headers = defaultHeaders(resource);
        if (resource.startsWith(REDIRECT_PREFIX)) {
            appendLocationHeader(headers, extractLocation(resource));
        }
        return resolveResponse(resource, headers);
    }

    private HttpResponse resolveResponse(String resource, HttpHeaders headers) {
        ContentType contentType = ContentType.from(headers.getHeader(CONTENT_TYPE));
        if (contentType.isHTML()) {
            return new HttpResponse(HttpProtocols.DEFAULT, HttpStatus.OK,
                    headers, FileIoUtils.loadFileFromClasspath(HTML_PATH + resource));
        }
        return new HttpResponse(HttpProtocols.DEFAULT, HttpStatus.OK,
                headers, FileIoUtils.loadFileFromClasspath(STATIC_PATH + resource));
    }

    private String extractLocation(String resource) {
        return resource.substring(resource.indexOf(REDIRECT_PREFIX) - 1);
    }

    private void appendLocationHeader(HttpHeaders headers, String redirectUri) {
        headers.addHeader("Location", redirectUri);
    }

    private HttpHeaders defaultHeaders(String resource) {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, getContentType(resource).getType());
        headers.put("Server", "ANDOLE MACHINE");
        return new HttpHeaders(headers);
    }

    private ContentType getContentType(String resource) {
        String extension = resource.substring(resource.lastIndexOf(EXTENSION_SEPARATOR) + 1);
        return ContentType.of(extension);
    }
}
