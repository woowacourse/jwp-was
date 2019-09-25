package webserver.support;

import webserver.exception.InvalidContentTypeException;

public class ContentTypeHandler {
    private static final String REGEX_FOR_TEXT_CONTENT_TYPE = "(html|css|plain|csv|woff)";
    private static final String REGEX_FOR_APPLICATION_CONTENT_TYPE = "(js|json|x-www-form-urlencoded|xml|zip|pdf)";
    private static final String REGEX_FOR_IMAGE_CONTENT_TYPE = "(png|jpeg|gif|ico)";
    private static final String TEXT_CONTENT_TYPE_FORMAT = "text/%s ;charset=utf-8";
    private static final String APPLICATION_CONTENT_TYPE_FORMAT = "application/%s";
    private static final String IMAGE_CONTENT_TYPE_FORMAT = "image/%s";

    public static String type(String url) {
        if (!url.contains(".")) {
            url += ".html";
        }

        String contentType = url.substring(url.lastIndexOf(".") + 1);

        if (contentType.matches(REGEX_FOR_TEXT_CONTENT_TYPE)) {
            return String.format(TEXT_CONTENT_TYPE_FORMAT, contentType);
        }

        if (contentType.matches(REGEX_FOR_APPLICATION_CONTENT_TYPE)) {
            return String.format(APPLICATION_CONTENT_TYPE_FORMAT, "js".equals(contentType) ? "javascript" : contentType);
        }

        if (contentType.matches(REGEX_FOR_IMAGE_CONTENT_TYPE)) {
            return String.format(IMAGE_CONTENT_TYPE_FORMAT, contentType);
        }

        throw new InvalidContentTypeException("invalid content type");
    }
}
