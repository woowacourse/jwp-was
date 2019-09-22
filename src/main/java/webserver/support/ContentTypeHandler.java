package webserver.support;

import webserver.exception.InvalidContentTypeException;

public class ContentTypeHandler {

    public static String type(String contentType) {
        if (contentType.matches("(html|css|plain|csv|woff)")) {
            return String.format("text/%s ;charset=utf-8", contentType);
        }

        if (contentType.matches("(js|json|x-www-form-urlencoded|xml|zip|pdf)")) {
            return String.format("application/%s", "js".equals(contentType) ? "javascript" : contentType);
        }

        if (contentType.matches("(png|jpeg|gif|ico)")) {
            return String.format("image/%s", contentType);
        }

        throw new InvalidContentTypeException("invalid content type");
    }
}
